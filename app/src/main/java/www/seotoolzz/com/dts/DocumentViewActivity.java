package www.seotoolzz.com.dts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Database.Models.Document;

public class DocumentViewActivity extends AppCompatActivity {
    String socket_base_url = "http://192.168.200.175:3030/";

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
    List<String> documentData;
    List<String> particularsData;

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
//
        String sample_data = "192.168.200.156/dts_admin_d70c9453e1f41d4624f2937b05819317/qrcodepage/@1$2021-03-00001$PGO.php|2021-03-00001|2021-03-22|PGO|JOEY GARCIA|1.00|FOR ITU USE|GENERALFUND|PGSO|1001|2|2021-03-22 11:22:23|1|['1'|'1'|'1'|'UNIT'|'LAPTOP ASUS'|'45000.00'|'45000.00'||'1'|'2'|'2'|'UNIT'|'LAPTOP ACER'|'55000.00'|'110000.00'||'1'|'3'|'3'|'UNIT'|'LAPTOP HP'|'35000.00'|'105000.00'||'1'|'4'|'4'|'UNIT'|'SAMSUNG A10'|'5000.00'|'20000.00'||'1'|'5'|'5'|'UNIT'|'CHERRY MOBILE S3'|'6000.00'|'30000.00']";
        splittedQRData = Arrays.asList(sample_data.split("\\["));

        documentData  = Arrays.asList(splittedQRData.get(0).split("\\|"));

        particularsData = Arrays.asList(splittedQRData.get(1).split("\\|\\|"));

        // Insert document to database.
        Document documentObject = new Document();

        documentObject.setReference_no(documentData.get(transaction_no));
        documentObject.setPr_date(documentData.get(pr_date));
        documentObject.setOffice(documentData.get(current_department));
        documentObject.setLaimant(documentData.get(pr_liason));
        documentObject.setParticulars(Arrays.toString(particularsData.toArray()));
        documentObject.setAmount(documentData.get(particulars_total_amount));
        documentObject.setPurpose(documentData.get(pr_purpose));
        documentObject.setCharge_to(documentData.get(pr_charge_to));
        documentObject.setCurrent_department(documentData.get(pr_current_department));
        documentObject.setCurrent_station(documentData.get(pr_station));

        DB.getInstance(this).documentDao().create(documentObject);

        referenceNo.setText(documentData.get(transaction_no));
        claimant.setText(documentData.get(pr_liason));
        office.setText(documentData.get(pr_office));
        purchaseDate.setText(documentData.get(pr_date));
        chargeTo.setText(documentData.get(pr_charge_to));
        currentDepartment.setText(documentData.get(current_department));

        JSONObject document = new JSONObject();
        try {

            /* BEGIN OF DATA */
                document.put("dtse0dce_data_reference_no", documentData.get(transaction_no));
                document.put("dtse0dce_data_pr_no", "");
                document.put("dtse0dce_data_pr_date", documentData.get(pr_date));
                document.put("dtse0dce_data_office", documentData.get(pr_office));
                document.put("dtse0dce_data_claimant", documentData.get(pr_liason));
                document.put("dtse0dce_data_particulars", "NO_INPUT");
                document.put("dtse0dce_data_amount", documentData.get(particulars_total_amount));
                document.put("dtse0dce_data_purpose", documentData.get(pr_purpose));
                document.put("dtse0dce_data_charge_to", documentData.get(pr_charge_to));
                document.put("dtse0dce_data_current_department", documentData.get(pr_current_department));
                document.put("dtse0dce_data_current_station", documentData.get(pr_station));
            /* END OF DATA */

            /* BEGIN OF HISTORY */
                document.put("dtsa6e7f_history_logs_user_id", documentData.get(user_id));
                document.put("dtsa6e7f_history_logs_datetime", documentData.get(datetime));
                document.put("dtsa6e7f_history_logs_office", documentData.get(pr_office));
                document.put("dtsa6e7f_history_logs_claimant", documentData.get(pr_liason));
                document.put("dtsa6e7f_history_logs_timelaps", "NONE");
                document.put("dtsa6e7f_history_logs_transaction", documentData.get(transaction_no));
                document.put("dtsa6e7f_history_logs_data_id", documentData.get(data_id));
                document.put("dtsa6e7f_history_logs_current_department", documentData.get(current_department));
                document.put("dtsa6e7f_history_logs_current_station", documentData.get(current_pr_station));
            /* END OF HISTORY */
            JSONArray particularObject = new JSONArray();
            JSONArray jsonArray = new JSONArray();

            for(int i = 0; i<particularsData.size(); i++) {
                String[] data = particularsData.get(i).split("\\|");
                JSONObject particular = new JSONObject();
                try {
                    particular.put("dts_particulars_item_no", data[0]);
                    particular.put("dts_particulars_quantity", data[1]);
                    particular.put("dts_particulars_data_id", data[2]);
                    particular.put("dts_particulars_unit_of_issue", data[3]);
                    particular.put("dts_particulars_item_description", data[4]);
                    particular.put("dts_particulars_estimated_unit_coast", data[5]);
                    particular.put("dts_particulars_estimated_total_coast", data[6]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                particularObject.put(particular);

                Log.d("NO_OF_ITEMS", (i + 1) + " => " + particularsData.get(i).replace("]", ""));
            }

            document.put("particulars", particularObject);

            Log.d("TEST_VALE", String.valueOf(document));


//            /* BEGIN OF PARTICULARS */
//                document.put("dts_particulars_item_no", splittedQRData.get(item_no));
//                document.put("dts_particulars_quantity", splittedQRData.get(quantity));
//                document.put("dts_particulars_data_id", splittedQRData.get(particulars_data_id));
//                document.put("dts_particulars_unit_of_issue", splittedQRData.get(unit_of_issue));
//                document.put("dts_particulars_item_description", splittedQRData.get(description));
//                document.put("dts_particulars_estimated_unit_coast", splittedQRData.get(estimated_unit_cost));
//                document.put("dts_particulars_estimated_total_coast", splittedQRData.get(estimated_total_cost));
//            /* END OF PARTICULARS */

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