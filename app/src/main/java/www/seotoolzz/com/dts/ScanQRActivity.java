
package www.seotoolzz.com.dts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

import java.util.ArrayList;
import java.util.List;

import www.seotoolzz.com.dts.Contracts.IQRScanner;

public class ScanQRActivity extends AppCompatActivity implements IQRScanner {
    LinearLayout scannerViewLayout;
    private CodeScanner mCodeScanner;
    private boolean hasManyQR = false;
    private boolean forClaim = false;
    List<String> bulkQrScanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        scannerViewLayout = findViewById(R.id.scannerViewLayout);



        mCodeScanner = new CodeScanner(this, scannerView);
        findViewById(R.id.btnOneQR).setOnClickListener(v -> {
            scannerViewLayout.setVisibility(View.VISIBLE);
            hideButton(R.id.proceed);
            hideButton(R.id.btnOneQR);
            hideButton(R.id.btnManyQR);
            hideButton(R.id.btnForClaim);
            mCodeScanner.startPreview();

        });


        findViewById(R.id.btnManyQR).setOnClickListener(v -> {
            scannerViewLayout.setVisibility(View.VISIBLE);
            showButton(R.id.proceed);
            hideButton(R.id.btnOneQR);
            hideButton(R.id.btnManyQR);
            hideButton(R.id.btnForClaim);
            mCodeScanner.startPreview();
            hasManyQR = true;
            forClaim = false;
        });

        findViewById(R.id.btnForClaim).setOnClickListener(v -> {
            scannerViewLayout.setVisibility(View.VISIBLE);
            hideButton(R.id.proceed);
            hideButton(R.id.btnOneQR);
            hideButton(R.id.btnManyQR);
            hideButton(R.id.btnForClaim);
            mCodeScanner.startPreview();
            hasManyQR = false;
            forClaim = true;
        });

        findViewById(R.id.proceed).setOnClickListener(v -> {
            for(String qr : bulkQrScanList) {
                Log.d("SAMPLE_DATA", qr);
            }
        });




        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            if(hasManyQR) {
                bulkQrScanList.add(result.getText().toLowerCase());
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
        hasManyQR = false;
        forClaim = false;
        if(scannerViewLayout.getVisibility() != View.GONE) {
            scannerViewLayout.setVisibility(View.GONE);
            showButton(R.id.btnOneQR);
            showButton(R.id.btnManyQR);
            showButton(R.id.btnForClaim);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void hideButton(int buttonId) {
        if(findViewById(buttonId).getVisibility() == View.VISIBLE) {
            findViewById(buttonId).setVisibility(View.GONE);
        }
    }

    @Override
    public void showButton(int buttonId) {
        if(findViewById(buttonId).getVisibility() == View.GONE) {
            findViewById(buttonId).setVisibility(View.VISIBLE);
        }
    }
}