package www.seotoolzz.com.dts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;

public class DocumentViewActivity extends AppCompatActivity {
    int transaction_no = 1;
    int pr_date = 2;
    int pr_office = 3;
    int pr_liason = 4;
    int particulars_total_amount = 5;
    int pr_purpose = 6;
    int pr_charge_to = 7;
    int pr_current_department = 8;
    int pr_station = 9;

    //  history
    int user_id = 10;
    int datetime = 11;
    int data_id = 12;
    int current_department = pr_current_department;
    int current_pr_station = pr_station;


    //  particulars
    int particulars_data_id  = data_id;
    int item_no  = 13;
    int quantity  = 14;
    int unit_of_issue  = 15;
    int description  = 16;
    int estimated_unit_cost  = 17;
    int estimated_total_cost  = 18;


    List<String> splittedQRData;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.0.108:3030");
        } catch (URISyntaxException e) {}
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocket.connect();

        setContentView(R.layout.activity_document_view);
        EditText referenceNo = findViewById(R.id.referenceNo);
        EditText claimant = findViewById(R.id.liaison_name);
        EditText office = findViewById(R.id.office);

        String QR_DATA = getIntent().getStringExtra("QR_DATA");
        splittedQRData = Arrays.asList(QR_DATA.split("\\|"));


        referenceNo.setText(splittedQRData.get(transaction_no));
        claimant.setText(splittedQRData.get(pr_liason));
        office.setText(splittedQRData.get(pr_office));

        Log.d("SAMPLE_QR_CODE", QR_DATA);

        JSONObject document = new JSONObject();
        try {


            /* BEGIN OF DATA */
                document.put("dtse0dce_data_reference_no", splittedQRData.get(transaction_no));
                document.put("dtse0dce_data_pr_no", "");
                document.put("dtse0dce_data_pr_date", splittedQRData.get(pr_date));
                document.put("dtse0dce_data_office", splittedQRData.get(pr_office));
                document.put("dtse0dce_data_claimant", splittedQRData.get(pr_liason));
                document.put("dtse0dce_data_particulars", "NO_INPUT");
                document.put("dtse0dce_data_amount", splittedQRData.get(particulars_total_amount));
                document.put("dtse0dce_data_purpose", splittedQRData.get(pr_purpose));
                document.put("dtse0dce_data_charge_to", splittedQRData.get(pr_charge_to));
                document.put("dtse0dce_data_current_department", splittedQRData.get(pr_current_department));
                document.put("dtse0dce_data_current_station", splittedQRData.get(pr_station));
            /* END OF DATA */


        } catch (JSONException e) {
            e.printStackTrace();
        }

        findViewById(R.id.btnSend).setOnClickListener(v -> mSocket.emit("SEND_PR_DATA", document));
    }
}