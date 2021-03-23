package www.seotoolzz.com.dts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class ScanQRActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
//    private Socket mSocket;
//    {
//        try {
//            mSocket = IO.socket("http://192.168.0.108:3030");
//        } catch (URISyntaxException e) {}
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mSocket.connect();
        setContentView(R.layout.activity_scan_q_r);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {

            // Redirect to new Activity with the data collected from QR.
            Intent intent = new Intent(ScanQRActivity.this, DocumentViewActivity.class);
            intent.putExtra("QR_DATA", result.getText());
            startActivity(intent);
//            mSocket.emit("SEND_PR_DATA", result.getText());
        }));

        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}