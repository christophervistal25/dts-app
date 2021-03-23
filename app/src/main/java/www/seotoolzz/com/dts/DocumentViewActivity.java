package www.seotoolzz.com.dts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import io.socket.emitter.Emitter;

public class DocumentViewActivity extends AppCompatActivity {
    String socket_base_url = "http://192.168.1.14:3030/";

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
            mSocket = IO.socket(socket_base_url);
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
        EditText purchaseDate = findViewById(R.id.prDate);
        EditText chargeTo = findViewById(R.id.chargeTo);
        EditText currentDepartment = findViewById(R.id.currentDepartment);

        String QR_DATA = getIntent().getStringExtra("QR_DATA");
        splittedQRData = Arrays.asList(QR_DATA.split("\\|"));


        referenceNo.setText(splittedQRData.get(transaction_no));
        claimant.setText(splittedQRData.get(pr_liason));
        office.setText(splittedQRData.get(pr_office));
        purchaseDate.setText(splittedQRData.get(pr_date));
        chargeTo.setText(splittedQRData.get(pr_charge_to));
        currentDepartment.setText(splittedQRData.get(current_department));

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

            /* BEGIN OF HISTORY */
                document.put("dtsa6e7f_history_logs_user_id", splittedQRData.get(user_id));
                document.put("dtsa6e7f_history_logs_datetime", splittedQRData.get(datetime));
                document.put("dtsa6e7f_history_logs_office", splittedQRData.get(pr_office));
                document.put("dtsa6e7f_history_logs_claimant", splittedQRData.get(pr_liason));
                document.put("dtsa6e7f_history_logs_timelaps", "NONE");
                document.put("dtsa6e7f_history_logs_transaction", splittedQRData.get(transaction_no));
                document.put("dtsa6e7f_history_logs_data_id", splittedQRData.get(data_id));
                document.put("dtsa6e7f_history_logs_current_department", splittedQRData.get(current_department));
                document.put("dtsa6e7f_history_logs_current_station", splittedQRData.get(current_pr_station));
            /* END OF HISTORY */

            /* BEGIN OF PARTICULARS */
                document.put("dts_particulars_item_no", splittedQRData.get(item_no));
                document.put("dts_particulars_quantity", splittedQRData.get(quantity));
                document.put("dts_particulars_data_id", splittedQRData.get(particulars_data_id));
                document.put("dts_particulars_unit_of_issue", splittedQRData.get(unit_of_issue));
                document.put("dts_particulars_item_description", splittedQRData.get(description));
                document.put("dts_particulars_estimated_unit_coast", splittedQRData.get(estimated_unit_cost));
                document.put("dts_particulars_estimated_total_coast", splittedQRData.get(estimated_total_cost));
            /* END OF PARTICULARS */



        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.on("data_passed", onSuccess);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DocumentViewActivity.this);
        dialogBuilder.setTitle("Important Message");
        dialogBuilder.setMessage("Please double check all the listed information before you submit this document.");
        dialogBuilder.setCancelable(true);

        dialogBuilder.setPositiveButton("I already double check", (dialog, id) -> mSocket.emit("SEND_PR_DATA", document));

        dialogBuilder.setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog confirmationDialog = dialogBuilder.create();

        findViewById(R.id.btnSend).setOnClickListener(v -> confirmationDialog.show());
    }

    private Emitter.Listener onSuccess = args -> runOnUiThread(() -> {
        JSONObject data = (JSONObject) args[0];
        String status;
        try {
            status = data.getString("success");
            if (status.equals("true")) {
                Toast.makeText(this, "Successfully send the document.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Something went wrong, please contact the developer of this app.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            return;
        }
    });
}