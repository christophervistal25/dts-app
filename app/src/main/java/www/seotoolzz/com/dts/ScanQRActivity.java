
package www.seotoolzz.com.dts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.tapadoo.alerter.Alerter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import www.seotoolzz.com.dts.Contracts.IQRScanner;
import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Database.Models.Document;
import www.seotoolzz.com.dts.Database.Models.DocumentRaw;

public class ScanQRActivity extends AppCompatActivity implements IQRScanner {
    LinearLayout scannerViewLayout;
    private CodeScanner mCodeScanner;
    private boolean hasManyQR = false;
    private boolean forClaim = false;
    List<String> bulkQrScanList = new ArrayList<>();
    private final String GLUE = "<>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        scannerViewLayout = findViewById(R.id.scannerViewLayout);



        mCodeScanner = new CodeScanner(this, scannerView);
        findViewById(R.id.btnOneQR).setOnClickListener(v -> {
            scannerViewLayout.setVisibility(View.VISIBLE);
            hideElement(R.id.proceed);
            hideElement(R.id.btnOneQR);
            hideElement(R.id.btnManyQR);
            hideElement(R.id.btnForClaim);
            hideElement(R.id.messageHelper);
            mCodeScanner.startPreview();

        });


        findViewById(R.id.btnManyQR).setOnClickListener(v -> {
            scannerViewLayout.setVisibility(View.VISIBLE);
            showElement(R.id.proceed);
            hideElement(R.id.btnOneQR);
            hideElement(R.id.btnManyQR);
            hideElement(R.id.btnForClaim);
            hideElement(R.id.messageHelper);
            mCodeScanner.startPreview();
            hasManyQR = true;
            forClaim = false;
        });

        findViewById(R.id.btnForClaim).setOnClickListener(v -> {
            scannerViewLayout.setVisibility(View.VISIBLE);
            hideElement(R.id.proceed);
            hideElement(R.id.btnOneQR);
            hideElement(R.id.btnManyQR);
            hideElement(R.id.btnForClaim);
            hideElement(R.id.messageHelper);
            mCodeScanner.startPreview();
            hasManyQR = false;
            forClaim = true;
        });

        findViewById(R.id.proceed).setOnClickListener(v -> {
            StringBuilder joinedData = new StringBuilder();

            for(String dataInQr : bulkQrScanList) {
                if(dataInQr.contains(",") || dataInQr.contains("|")) {
                    joinedData.append(GLUE).append(dataInQr);
                }
            }

            String QR_DATA = joinedData.toString();
            String[] data = QR_DATA.split("<>");

            String documentData = this.getDocumentInformation(data);

            String referenceNo = documentData.split("\\|")[0];

            DocumentRaw documentRaw = new DocumentRaw();
            documentRaw.setReference_no(referenceNo);
            documentRaw.setData(joinedData.toString());

            DB.getInstance(getApplicationContext()).documentRawDao().create(documentRaw);

            Intent intent = new Intent(ScanQRActivity.this, DocumentViewActivity.class);
            intent.putExtra("QR_DATA", joinedData.toString());
            startActivity(intent);
        });




        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            if(hasManyQR) {

                String QR_DATA = result.getText().toLowerCase().replace("[", "").replace("]", "") + "|";

                if(!bulkQrScanList.contains(QR_DATA)) {
                    bulkQrScanList.add(QR_DATA);

                    // Add no. of scanned qr in button text.
                    Button btnProceed = findViewById(R.id.proceed);
                    btnProceed.setText(String.format(getString(R.string.btn_proceed_scan), bulkQrScanList.size()));

                    Alerter.create(this)
                                .setTitle("QR Scanner")
                                .setText("QR Successfully Scanned")
                                .enableSwipeToDismiss()
                                .show();
                } else {
                    Toast.makeText(this, "QR ALREADY SCANNED", Toast.LENGTH_SHORT).show();
                }
                mCodeScanner.startPreview();
            } else if(forClaim) {
                int REFERENCE_INDEX = 0;
                int OFFICE_INDEX = 2;
                String[] documentData = result.getText().toLowerCase().split("\\|");
                String dynamicUrl = "$" + documentData[REFERENCE_INDEX] + "$" + documentData[OFFICE_INDEX].toUpperCase() + ".php";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.1.11/dts_admin_d70c9453e1f41d4624f2937b05819317/qrcodepage/@" + dynamicUrl));
                startActivity(browserIntent);
            } else {
                Intent intent = new Intent(ScanQRActivity.this, DocumentViewActivity.class);
                intent.putExtra("QR_DATA", result.getText());
                startActivity(intent);
            }
        }));
//
    }


    private String getParticularsData(String[] data) {
        StringBuilder particulars = new StringBuilder();

        for(String d: data) {
            if(d.split("\\|").length <= 6) {
                particulars.append(d);
            }
        }

        return particulars.toString();
    }

    private String getDocumentInformation(String[] data) {
        String documentData = "";
        for(String d : data) {
            if(d.split("\\|").length >= 10) {
                documentData = d;
                break;
            }
        }
        return documentData;
    }






    @Override
    protected void onResume() {
        super.onResume();
        if(mCodeScanner != null) {
            mCodeScanner.startPreview();
        }

    }

    @Override
    protected void onPause() {
        if(mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {

        if(scannerViewLayout.getVisibility() != View.GONE) {
            hasManyQR = false;
            forClaim = false;
            scannerViewLayout.animate().alpha(0.0f);
            scannerViewLayout.setVisibility(View.GONE);
//            showElement(R.id.btnOneQR);
            showElement(R.id.btnManyQR);
            showElement(R.id.btnForClaim);
            showElement(R.id.messageHelper);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void hideElement(int buttonId) {
        if(findViewById(buttonId).getVisibility() == View.VISIBLE) {
            findViewById(buttonId).setVisibility(View.GONE);
        }
    }

    @Override
    public void showElement(int buttonId) {
        if(findViewById(buttonId).getVisibility() == View.GONE) {
            findViewById(buttonId).setVisibility(View.VISIBLE);
        }
    }
}