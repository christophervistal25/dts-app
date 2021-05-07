package www.seotoolzz.com.dts.API.Contracts;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import www.seotoolzz.com.dts.API.ContractModels.UserLoginResponse;

public interface IDocument {

    @FormUrlEncoded
    @POST("/dts_admin_d70c9453e1f41d4624f2937b05819317/c79bdf421714f5087fc34b7c538b6807/transaction/added_btn_data.php")
    Call<UserLoginResponse> sendDocument(@Field("data_reference_no") String data_reference_no,
                                         @Field("data_pr_date") String data_pr_date,
                                         @Field("data_office") String data_office,
                                         @Field("data_claimant") String data_claimant,
                                         @Field("data_amount") String data_amount,
                                         @Field("data_purpose") String data_purpose,
                                         @Field("data_charge_to") String data_charge_to,
                                         @Field("data_current_department") String data_current_department,
                                         @Field("data_current_station") String data_current_station,
                                         @Field("history_logs_datetime") String history_logs_datetime,
                                         @Field("history_logs_datetime") String history_logs_user_id
    );

    @FormUrlEncoded
    @POST("/dts_admin_d70c9453e1f41d4624f2937b05819317/c79bdf421714f5087fc34b7c538b6807/transaction/added_btn_history_logs.php")
    Call<UserLoginResponse> sendHistoryOfDocument(@Field("data_reference_no") String data_reference_no,
                                         @Field("history_logs_office") String history_logs_office,
                                         @Field("history_logs_user_id") String history_logs_user_id,
                                         @Field("history_logs_datetime") String history_logs_datetime,
                                         @Field("history_logs_claimant") String history_logs_claimant,
                                         @Field("history_logs_current_station") String history_logs_current_station,
                                         @Field("history_logs_current_department") String history_logs_current_department
    );

    @FormUrlEncoded
    @POST("/dts_admin_d70c9453e1f41d4624f2937b05819317/c79bdf421714f5087fc34b7c538b6807/transaction/added_btn_particulars.php")
    Call<UserLoginResponse> sendParticulars(@Field("data_reference_no") String data_reference_no,
                                                  @Field("particulars") String particulars
    );

}
