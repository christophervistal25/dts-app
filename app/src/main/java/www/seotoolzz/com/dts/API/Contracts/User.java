package www.seotoolzz.com.dts.API.Contracts;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import www.seotoolzz.com.dts.API.ContractModels.UserLoginResponse;

public interface User {

    @FormUrlEncoded
    @POST("/mobile/login.php")
    Call<UserLoginResponse> login(@Field("username") String username, @Field("password") String password);



}

