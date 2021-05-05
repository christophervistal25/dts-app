
package www.seotoolzz.com.dts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import www.seotoolzz.com.dts.Contracts.IQRScanner;

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

            Intent intent = new Intent(ScanQRActivity.this, DocumentViewActivity.class);
            intent.putExtra("QR_DATA", joinedData.toString());
            startActivity(intent);
        });




        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            if(hasManyQR) {
                if(!bulkQrScanList.contains(result.getText().toLowerCase())) {
                    bulkQrScanList.add(result.getText().toLowerCase());

                    // Add no. of scanned qr in button text.
                    Button btnProceed = findViewById(R.id.proceed);
                    btnProceed.setText(String.format("PROCEED WITH %d QR SCANNED", bulkQrScanList.size()));

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
                Toast.makeText(this, "For Claim", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(ScanQRActivity.this, DocumentViewActivity.class);
                intent.putExtra("QR_DATA", result.getText());
                startActivity(intent);
            }
        }));
//
    }

//    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + data[0]));
    //            startActivity(browserIntent);
    // Redirect to new Activity with the data collected from QR.
//    Intent intent = new Intent(ScanQRActivity.this, DocumentViewActivity.class);
//            intent.putExtra("QR_DATA", result.getText());
//    startActivity(intent);
//            mSocket.emit("SEND_PR_DATA", result.getText());



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