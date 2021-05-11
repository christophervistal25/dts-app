
package www.seotoolzz.com.dts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import www.seotoolzz.com.dts.Contracts.IQRScanner;
import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Database.Models.DocumentRaw;
import www.seotoolzz.com.dts.Helpers.SharedPref;

public class ScanQRActivity extends AppCompatActivity implements IQRScanner {
    LinearLayout scannerViewLayout;
    private CodeScanner mCodeScanner;
    private boolean hasManyQR = false;
    List<String> bulkQrScanList = new ArrayList<>();
    private final String GLUE = "<>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        scannerViewLayout = findViewById(R.id.scannerViewLayout);

        mCodeScanner = new CodeScanner(this, scannerView);


        findViewById(R.id.btnManyQR).setOnClickListener(v -> {
            scannerViewLayout.setVisibility(View.VISIBLE);
            showElement(R.id.proceed);
            hideElement(R.id.btnOneQR);
            hideElement(R.id.btnManyQR);
            hideElement(R.id.btnForClaim);
            hideElement(R.id.messageHelper);
            mCodeScanner.startPreview();
            hasManyQR = true;
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
            String uniqueID = UUID.randomUUID().toString();

            DocumentRaw documentRaw = new DocumentRaw();
            documentRaw.setReference_no(referenceNo);
            documentRaw.setData(joinedData.toString());
            documentRaw.setUnique_id(uniqueID);

            DB.getInstance(getApplicationContext()).documentRawDao().create(documentRaw);

            Intent intent = new Intent(ScanQRActivity.this, DocumentViewActivity.class);
            intent.putExtra("UNIQUE_ID", uniqueID);
            intent.putExtra("QR_DATA", joinedData.toString());
            startActivity(intent);
        });




        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            if(hasManyQR) {
                if(!this.isDataValid(result.getText())) {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                    Toast toast = Toast.makeText(ScanQRActivity.this, "Please re-scan the QR", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mCodeScanner.startPreview();
                } else {
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
                }


            } else {
                int REFERENCE_INDEX = 0;
                int OFFICE_INDEX = 2;
                String[] documentData = result.getText().toLowerCase().split("\\|");
                String dynamicUrl = "$" + documentData[REFERENCE_INDEX] + "$" + documentData[OFFICE_INDEX].toUpperCase() + ".php";
                String base_url = SharedPref.getSharedPreferenceString(getApplicationContext(), "BASE_URL", getString(R.string.base_url));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(base_url + "dts_admin_d70c9453e1f41d4624f2937b05819317/qrcodepage/@" + dynamicUrl));
                startActivity(browserIntent);
            }
        }));
    }

    private boolean isDataValid(String text) {
        return text.length() >= 9;
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
            scannerViewLayout.setVisibility(View.GONE);
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