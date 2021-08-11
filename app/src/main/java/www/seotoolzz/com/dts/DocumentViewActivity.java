package www.seotoolzz.com.dts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tapadoo.alerter.Alerter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import www.seotoolzz.com.dts.API.ContractModels.DocumentSendResponse;
import www.seotoolzz.com.dts.API.Contracts.IDocument;
import www.seotoolzz.com.dts.API.RetrofitService;
import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Database.Models.Document;
import www.seotoolzz.com.dts.Database.Models.DocumentRaw;
import www.seotoolzz.com.dts.Helpers.DocumentHelper;
import www.seotoolzz.com.dts.Helpers.SharedPref;

public class DocumentViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_view);
        DocumentHelper documetHelper = new DocumentHelper();


        EditText referenceNo = findViewById(R.id.referenceNo);
        EditText claimant = findViewById(R.id.claimant);
        EditText office = findViewById(R.id.office);
        EditText chargeTo = findViewById(R.id.chargeTo);
        EditText currentDepartment = findViewById(R.id.currentDepartment);
        EditText currentStation = findViewById(R.id.current_station);
        EditText dataTransaction = findViewById(R.id.transaction);

        String splintedDocumentData[] = getIntent().getStringExtra("QR_DATA").split(documetHelper.SEPERATOR_PATTERN);

        if(DB.getInstance(this).documentDao().find(splintedDocumentData[DocumentHelper.REFERENCE_NO_INDEX]) == null) {
            DocumentRaw documentRaw = new DocumentRaw();
            documentRaw.setReference_no(splintedDocumentData[DocumentHelper.REFERENCE_NO_INDEX]);
            documentRaw.setData(getIntent().getStringExtra("QR_DATA"));
            DB.getInstance(this).documentRawDao().create(documentRaw);

            Document document = new Document();
            document.setReference_no(splintedDocumentData[DocumentHelper.REFERENCE_NO_INDEX]);
            document.setClaimant(splintedDocumentData[DocumentHelper.CLAIMANT_INDEX]);
            document.setOffice(splintedDocumentData[DocumentHelper.OFFICE_INDEX]);
            document.setTransaction(splintedDocumentData[DocumentHelper.TRANSACTION_INDEX]);
            document.setCurrent_department(splintedDocumentData[DocumentHelper.OFFICE_INDEX]);
            document.setCurrent_station(splintedDocumentData[DocumentHelper.CURRENT_STATION_INDEX]);
            document.setLogs_user_id(splintedDocumentData[DocumentHelper.LOGS_USER_ID_INDEX]);
            document.setHistory_logs_datetime(splintedDocumentData[DocumentHelper.HISTORY_LOGS_DATETIME_INDEX]);
            document.setCharge_to(SharedPref.getSharedPreferenceString(getApplicationContext(), "SELECTED_OFFICE", ""));
            DB.getInstance(getApplicationContext()).documentDao().create(document);
        }

        referenceNo.setText(splintedDocumentData[DocumentHelper.REFERENCE_NO_INDEX]);
        claimant.setText(splintedDocumentData[DocumentHelper.CLAIMANT_INDEX].toUpperCase());
        office.setText(splintedDocumentData[DocumentHelper.OFFICE_INDEX]);
        dataTransaction.setText(splintedDocumentData[DocumentHelper.TRANSACTION_INDEX]);
        chargeTo.setText(SharedPref.getSharedPreferenceString(getApplicationContext(), "SELECTED_OFFICE", ""));
        currentDepartment.setText(splintedDocumentData[DocumentHelper.OFFICE_INDEX].toUpperCase());
        currentStation.setText(splintedDocumentData[DocumentHelper.CURRENT_STATION_INDEX].toUpperCase());
        
        findViewById(R.id.btnSend).setOnClickListener(v -> sendDataOfDocument(splintedDocumentData));
    }

    public void sendDataOfDocument(String[] splintedDocumentData)
    {
        ProgressDialog progressDialog = new ProgressDialog(DocumentViewActivity.this);
        progressDialog.setMessage("This may take a few seconds...");
        progressDialog.show();
        Retrofit retrofit = RetrofitService
                                    .RetrofitInstance(SharedPref.getSharedPreferenceString(getApplicationContext(), "BASE_URL", getString(R.string.base_url)));

        IDocument documentService = retrofit.create(IDocument.class);

        Call<DocumentSendResponse> call = documentService
                .sendDocument(
                        splintedDocumentData[DocumentHelper.REFERENCE_NO_INDEX],
                        splintedDocumentData[DocumentHelper.OFFICE_INDEX],
                        splintedDocumentData[DocumentHelper.TRANSACTION_INDEX],
                        splintedDocumentData[DocumentHelper.CLAIMANT_INDEX],
                        SharedPref.getSharedPreferenceString(getApplicationContext(), "SELECTED_OFFICE", ""),
                        splintedDocumentData[DocumentHelper.CURRENT_DEPARTMENT_INDEX],
                        splintedDocumentData[DocumentHelper.CURRENT_STATION_INDEX],
                        splintedDocumentData[DocumentHelper.HISTORY_LOGS_DATETIME_INDEX],
                        splintedDocumentData[DocumentHelper.LOGS_USER_ID_INDEX]
                );

        call.enqueue(new Callback<DocumentSendResponse>() {
            @Override
            public void onResponse(Call<DocumentSendResponse> call, Response<DocumentSendResponse> response) {
                if(response.code() == 200) {
                    sendHistoryCallback(splintedDocumentData, progressDialog);
                }
            }

            @Override
            public void onFailure(Call<DocumentSendResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void sendHistoryCallback(String[] splintedDocumentData, ProgressDialog progressDialog) {
        Retrofit r = RetrofitService
                .RetrofitInstance(SharedPref.getSharedPreferenceString(getApplicationContext(), "BASE_URL", getString(R.string.base_url)));

        IDocument service = r.create(IDocument.class);

        Call<DocumentSendResponse> call =  service.sendHistoryOfDocument(
                splintedDocumentData[DocumentHelper.CURRENT_DEPARTMENT_INDEX],
                splintedDocumentData[DocumentHelper.REFERENCE_NO_INDEX],
                splintedDocumentData[DocumentHelper.OFFICE_INDEX],
                splintedDocumentData[DocumentHelper.LOGS_USER_ID_INDEX],
                splintedDocumentData[DocumentHelper.HISTORY_LOGS_DATETIME_INDEX],
                splintedDocumentData[DocumentHelper.CLAIMANT_INDEX],
                splintedDocumentData[DocumentHelper.TRANSACTION_INDEX],
                splintedDocumentData[DocumentHelper.CURRENT_STATION_INDEX]
        );

        call.enqueue(new Callback<DocumentSendResponse>() {
             @Override
             public void onResponse(Call<DocumentSendResponse> call, Response<DocumentSendResponse> response) {
                 progressDialog.dismiss();

                 if(response.code() == 200) {

                    /* UPDATE THE CURRENT DOCUMENT TO SENT. */
                     Document document = DB.getInstance(getApplicationContext()).documentDao().find(splintedDocumentData[DocumentHelper.REFERENCE_NO_INDEX]);
                     document.setStatus("SENT");
                     DB.getInstance(getApplicationContext()).documentDao().update(document);


                     Alerter.create(DocumentViewActivity.this)
                             .setTitle("Message")
                             .setBackgroundColorRes(R.color.colorPrimary)
                             .setText("Document data successfully send")
                             .enableSwipeToDismiss()
                             .show();
                 } else {
                     Alerter.create(DocumentViewActivity.this)
                             .setTitle("Message")
                             .setBackgroundColorRes(R.color.colorDanger)
                             .setText("Something went wrong please contact the developer")
                             .enableSwipeToDismiss()
                             .show();
                 }

             }

             @Override
             public void onFailure(Call<DocumentSendResponse> call, Throwable t) {
                 progressDialog.dismiss();
                 Toast.makeText(DocumentViewActivity.this, "Opps! Please contact the developer there's a problem", Toast.LENGTH_SHORT).show();
             }
        });
    }


}