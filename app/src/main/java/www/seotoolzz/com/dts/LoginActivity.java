package www.seotoolzz.com.dts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import www.seotoolzz.com.dts.API.ContractModels.UserLoginResponse;
import www.seotoolzz.com.dts.API.Contracts.User;
import www.seotoolzz.com.dts.API.RetrofitService;
import www.seotoolzz.com.dts.Database.DB;
import www.seotoolzz.com.dts.Helpers.SharedPref;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPref.getSharedPreferenceBoolean(this, "IS_LOGGED_IN", false)) {
            redirectToMainActivity();
        }

        EditText userName = findViewById(R.id.username);
        EditText userPassword = findViewById(R.id.password);

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait ...");


        findViewById(R.id.btnSignIn).setOnClickListener(v -> {

            // Check if the user is logged in locally.
            www.seotoolzz.com.dts.Database.Models.User user = DB.getInstance(getApplicationContext()).userDao().findByUsername(userName.getText().toString());
            progressDialog.show();

            if(user != null) {
                // Check credentials locally
                if(user.getUser_account_username().equals(userName.getText().toString())
                        && user.getUser_account_password().equals(userPassword.getText().toString())) {
                    // Set user logged in to true
                    SharedPref.setSharedPreferenceBoolean(getApplicationContext(), "IS_LOGGED_IN", true);
                    SharedPref.setSharedPreferenceString(getApplicationContext(), "NAME", user.getUser_account_name());
                    // Redirect the user to main page.
                    redirectToMainActivity();

                } else {
                    userName.setError("Please check your username or password.");
                }
            } else {
                Retrofit retrofit = RetrofitService.RetrofitInstance(getString(R.string.base_url));

                User userService = retrofit.create(User.class);

                Call<UserLoginResponse> userLoginResponseCall = userService.login(userName.getText().toString(), userPassword.getText().toString());
                userLoginResponseCall.enqueue(new Callback<UserLoginResponse>() {
                    @Override
                    public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                        if(response.body().getCode().equals("200")) {
                            progressDialog.dismiss();

                            // Insert data to dashboard.
                            insertNewUser(response.body());

                            www.seotoolzz.com.dts.Database.Models.User
                                    account = new www.seotoolzz.com.dts.Database.Models.User();

                            account.setUser_account_id(response.body().getUser_account_id());
                            account.setUser_account_md5_id(response.body().getUser_account_md5_id());
                            account.setUser_account_image(response.body().getUser_account_image());
                            account.setUser_account_name(response.body().getUser_account_name());
                            account.setUser_account_department(response.body().getUser_account_username());
                            account.setUser_account_station(response.body().getUser_account_station());
                            account.setUser_account_contact(response.body().getUser_account_contact());
                            account.setUser_account_designation(response.body().getUser_account_designation());
                            account.setUser_account_status(response.body().getUser_account_status());
                            account.setUser_account_datetime(response.body().getUser_account_datetime());
                            account.setUser_account_username(response.body().getUser_account_username());
                            account.setUser_account_password(response.body().getUser_account_password());
                            account.setUser_account_security(response.body().getUser_account_security());
                            account.setUser_account_active(response.body().getUser_account_active());
                            account.setUser_account_text_code(response.body().getUser_account_text_code());
                            account.setUser_account_code1(response.body().getUser_account_code1());
                            account.setUser_account_code2(response.body().getUser_account_code2());
                            account.setUser_account_code3(response.body().getUser_account_code3());

                            if(DB.getInstance(getApplicationContext()).userDao().create(account) != 0) {

                                // Set user logged in to true
                                SharedPref.setSharedPreferenceString(getApplicationContext(), "USERNAME", account.getUser_account_username());

                                SharedPref.setSharedPreferenceBoolean(getApplicationContext(), "IS_LOGGED_IN", true);

                                // Redirect the user to main page.
                                redirectToMainActivity();
                            }


                        } else {
                            progressDialog.dismiss();
                            userName.setError("Please check your username or password.");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });


    }

    private void redirectToMainActivity() {
        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
    }

    private void insertNewUser(UserLoginResponse body) {

    }
}