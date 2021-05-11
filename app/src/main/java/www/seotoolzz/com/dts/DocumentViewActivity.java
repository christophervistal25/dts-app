package www.seotoolzz.com.dts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import www.seotoolzz.com.dts.API.ContractModels.UserLoginResponse;
import www.seotoolzz.com.dts.API.Contracts.IDocument;
import www.seotoolzz.com.dts.API.RetrofitService;
import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Database.Models.Document;
import www.seotoolzz.com.dts.Helpers.SharedPref;

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

    //<>2021-05-10001-00001|2021-05-04|pgo|ervy m. isiang|1.00|for itu use|generalfund|pgso|1001|1|2021-05-04 14:59:22|1|
    int QR_DATA_REFERENCE_NO = 0;
    int QR_DATA_PURCHASE_REQUEST_DATE = 1;
    int QR_DATA_OFFICE = 2;
    int QR_DATA_LIASON_NAME = 3;
    int QR_DATA_AMOUNT = 4;
    int QR_DATA_PURPOSE = 5;
    int QR_DATA_CHARGED_TO = 6;
    int QR_DATA_CURRENT_DEPARTMENT = 7;
    int QR_DATA_CURRENT_STATION = 8;
    int QR_DATA_USER_ID = 9;
    int QR_DATA_DATE_TIME = 10;



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
        EditText amount = findViewById(R.id.amount);
        EditText purpose = findViewById(R.id.purpose);
        EditText currentStation = findViewById(R.id.current_station);


        String QR_DATA = getIntent().getStringExtra("QR_DATA");
        String[] data = QR_DATA.split("<>");

        String documentData = this.getDocumentInformation(data);
        String particularsData = this.getParticularsData(data).replaceAll("\\|\\|", "|");
        Log.d("SAMPLE_DATA", particularsData);
        String[] splintedDocumentData = documentData.split("\\|");

        referenceNo.setText(splintedDocumentData[QR_DATA_REFERENCE_NO]);
        claimant.setText(splintedDocumentData[QR_DATA_LIASON_NAME].toUpperCase());
        office.setText(splintedDocumentData[QR_DATA_OFFICE].toUpperCase());
        purchaseDate.setText(splintedDocumentData[QR_DATA_PURCHASE_REQUEST_DATE]);
        chargeTo.setText(splintedDocumentData[QR_DATA_CHARGED_TO].toUpperCase());
        currentDepartment.setText(splintedDocumentData[QR_DATA_CURRENT_DEPARTMENT].toUpperCase());
        amount.setText(splintedDocumentData[QR_DATA_AMOUNT]);
        purpose.setText(splintedDocumentData[QR_DATA_PURPOSE].toUpperCase());
        currentStation.setText(splintedDocumentData[QR_DATA_CURRENT_STATION].toUpperCase());


        // Checking if there's a particular can't send document to API.
        if(particularsData.isEmpty()) {
            Alerter.create(this)
                    .setTitle("Message")
                    .setText("No available particulars please re-scan the document.")
                    .enableSwipeToDismiss()
                    .show();
        }

        // Can't view particular activity if there's no particular for the current scan document.
        findViewById(R.id.viewParticulars).setOnClickListener(v -> {
            if(particularsData.isEmpty()) {
                Alerter.create(this)
                        .setTitle("Message")
                        .setText("No available particulars please re-scan the document.")
                        .enableSwipeToDismiss()
                        .show();
            } else {
                Intent intent = new Intent(DocumentViewActivity.this, ParticularsActivity.class);
                intent.putExtra("PARTICULARS", particularsData);
                startActivity(intent);
            }
        });



        if(getIntent().getStringExtra("TYPE") == null || !getIntent().getStringExtra("TYPE").toUpperCase().equals("VIEW")) {
            // Insert document to database.
            String uniqueID = getIntent().getStringExtra("UNIQUE_ID");


            Document documentObject = new Document();
            documentObject.setReference_no(splintedDocumentData[QR_DATA_REFERENCE_NO]);
            documentObject.setPr_date(splintedDocumentData[QR_DATA_PURCHASE_REQUEST_DATE]);
            documentObject.setOffice(splintedDocumentData[QR_DATA_OFFICE]);
            documentObject.setLaimant(splintedDocumentData[QR_DATA_LIASON_NAME]);
            documentObject.setParticulars(particularsData);
            documentObject.setAmount(splintedDocumentData[QR_DATA_AMOUNT]);
            documentObject.setPurpose(splintedDocumentData[QR_DATA_PURPOSE]);
            documentObject.setCharge_to(splintedDocumentData[QR_DATA_CHARGED_TO]);
            documentObject.setCurrent_department(splintedDocumentData[QR_DATA_CURRENT_DEPARTMENT]);
            documentObject.setCurrent_station(splintedDocumentData[QR_DATA_CURRENT_STATION]);
            documentObject.setUnique_id(uniqueID);

            DB.getInstance(this).documentDao().create(documentObject);
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DocumentViewActivity.this);
        dialogBuilder.setTitle("Important Message");
        dialogBuilder.setMessage("Please double check all the listed information before you submit this document.");
        dialogBuilder.setCancelable(true);

        dialogBuilder.setPositiveButton("I already double check", (dialog, id) -> {

//            Toast.makeText(this, particularsData, Toast.LENGTH_SHORT).show();
              // build data for particulars.
              String prepareDate = particularsData.replace("'", "");
              String output = prepareDate.replace("|", ",|,");
              String preparedData = "\"" + output.substring(0, output.length() - 1) + "\"";

            Retrofit retrofit = RetrofitService.RetrofitInstance(SharedPref.getSharedPreferenceString(getApplicationContext(), "BASE_URL", getString(R.string.base_url)));


            IDocument documentService = retrofit.create(IDocument.class);
            Call<UserLoginResponse> documentResponseCall = documentService
                                        .sendDocument(splintedDocumentData[QR_DATA_REFERENCE_NO],splintedDocumentData[QR_DATA_PURCHASE_REQUEST_DATE],
                                                splintedDocumentData[QR_DATA_OFFICE].toUpperCase(),splintedDocumentData[QR_DATA_LIASON_NAME].toUpperCase(),splintedDocumentData[QR_DATA_AMOUNT],
                                                splintedDocumentData[QR_DATA_PURPOSE].toUpperCase(),splintedDocumentData[QR_DATA_CHARGED_TO].toUpperCase(),splintedDocumentData[QR_DATA_CURRENT_DEPARTMENT].toUpperCase(),
                                                splintedDocumentData[QR_DATA_CURRENT_STATION].toUpperCase(),
                                                splintedDocumentData[QR_DATA_DATE_TIME],
                                                splintedDocumentData[QR_DATA_USER_ID]
                                        );

            documentResponseCall.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {

                }

                @Override
                public void onFailure(Call<UserLoginResponse> call, Throwable t) {}
            });


            IDocument iDocument = retrofit.create(IDocument.class);
            Call<UserLoginResponse> documentResponse = iDocument
                    .sendHistoryOfDocument(
                            splintedDocumentData[QR_DATA_REFERENCE_NO],
                            splintedDocumentData[QR_DATA_OFFICE].toUpperCase(),
                            splintedDocumentData[QR_DATA_USER_ID],
                            splintedDocumentData[QR_DATA_DATE_TIME],
                            splintedDocumentData[QR_DATA_LIASON_NAME].toUpperCase(),
                            splintedDocumentData[QR_DATA_CURRENT_STATION].toUpperCase(),
                            splintedDocumentData[QR_DATA_CURRENT_DEPARTMENT].toUpperCase()
                    );

            documentResponse.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {

                }

                @Override
                public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                    if(t.getMessage().toLowerCase().contains("end of input")) {
                        Toast toast = Toast.makeText(DocumentViewActivity.this, "Document successfully send", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        finish();
                    } else {
                        Toast.makeText(DocumentViewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            IDocument iParticulars = retrofit.create(IDocument.class);
            Call<UserLoginResponse> particularsCall = iParticulars
                    .sendParticulars(
                            splintedDocumentData[QR_DATA_REFERENCE_NO],
                            preparedData
                    );

            particularsCall.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {

                }

                @Override
                public void onFailure(Call<UserLoginResponse> call, Throwable t) {
             
                }
            });


        });

        dialogBuilder.setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        AlertDialog confirmationDialog = dialogBuilder.create();

        findViewById(R.id.btnSend).setOnClickListener(v -> {

            if(particularsData.isEmpty()) {
                Alerter.create(this)
                        .setTitle("Message")
                        .setText("No available particulars please re-scan the document.")
                        .enableSwipeToDismiss()
                        .show();
                return;
            }

            confirmationDialog.show();
        });
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

    private Emitter.Listener onSuccess = args -> runOnUiThread(() -> {
        JSONObject data = (JSONObject) args[0];
        String status;
        try {
            status = data.getString("success");
            if (status.equals("true")) {
                Toast.makeText(this, "Successfully send the document.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (JSONException e) {
            return;
        }
    });
}