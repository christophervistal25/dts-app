
package www.seotoolzz.com.dts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.zxing.Result;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

import www.seotoolzz.com.dts.Contracts.IQRScanner;
import www.seotoolzz.com.dts.Helpers.DocumentHelper;
import www.seotoolzz.com.dts.Helpers.SharedPref;

public class ScanQRActivity extends AppCompatActivity implements IQRScanner {
    LinearLayout scannerViewLayout;
    private CodeScanner mCodeScanner;
    DocumentHelper documentHelper;
    List<String> documentsReferenceNo = new ArrayList<>();


    boolean forProcessingDocument = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r);

        documentsReferenceNo = new ArrayList<>();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        scannerViewLayout = findViewById(R.id.scannerViewLayout);

        mCodeScanner = new CodeScanner(this, scannerView);
        documentHelper = new DocumentHelper();


        findViewById(R.id.btnManyQR).setOnClickListener(v -> {
            scannerViewLayout.setVisibility(View.VISIBLE);
            hideElement(R.id.btnOneQR);
            hideElement(R.id.btnManyQR);
            hideElement(R.id.btnForClaim);
            hideElement(R.id.messageHelper);
            mCodeScanner.startPreview();
            forProcessingDocument = true;
        });

        findViewById(R.id.btnForClaim).setOnClickListener(v -> {
            scannerViewLayout.setVisibility(View.VISIBLE);
            hideElement(R.id.btnOneQR);
            hideElement(R.id.btnManyQR);
            hideElement(R.id.btnForClaim);
            hideElement(R.id.messageHelper);
            mCodeScanner.startPreview();
            forProcessingDocument = false;
        });


            mCodeScanner.setAutoFocusEnabled(true);

            mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
               String[] data = documentHelper.extract(result.getText());
               // Check if data has a reference no.
                if(documentHelper.isReferenceNo(data[DocumentHelper.REFERENCE_NO_INDEX])) {
                    if(!documentsReferenceNo.contains(data[DocumentHelper.REFERENCE_NO_INDEX])) {
                        if(forProcessingDocument) {
                            redirectToActivity(result, data[DocumentHelper.REFERENCE_NO_INDEX]);
                        } else {
                            this.redirectToAPage(data);
                        }
                    }
                } else {
                    Alerter.create(this)
                            .setTitle("Message")
                            .setBackgroundColorRes(R.color.colorDanger)
                            .setText("Please re-scan the QR-Code.")
                            .enableSwipeToDismiss()
                            .show();
                }
                mCodeScanner.startPreview();
            }));
    }

    private void redirectToActivity(Result result, String datum) {
        documentsReferenceNo.add(datum);
        Intent intent = new Intent(ScanQRActivity.this, DocumentViewActivity.class);
        intent.putExtra("QR_DATA", result.getText());
        startActivity(intent);
    }

    private void redirectToAPage(String[] data) {
        String dynamicUrl = "$" + data[DocumentHelper.REFERENCE_NO_INDEX] + "$" + data[DocumentHelper.OFFICE_INDEX].toUpperCase() + ".php";
        String base_url = SharedPref.getSharedPreferenceString(getApplicationContext(), "BASE_URL", getString(R.string.base_url));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(base_url + "dts_admin_d70c9453e1f41d4624f2937b05819317/qrcodepage/@" + dynamicUrl));
        startActivity(browserIntent);
    }

@Override
    protected void onResume() {
        super.onResume();
        documentsReferenceNo = new ArrayList<>();
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