package com.example.crudretrofit.login.login_ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crudretrofit.R;
import com.example.crudretrofit.login.loginmodels.LoginRequest;
import com.example.crudretrofit.login.loginmodels.LoginResponse;
import com.example.crudretrofit.login.loginmodels.LoginUser;
import com.example.crudretrofit.retrofit.ApiInterface;
import com.example.crudretrofit.retrofit.RetrofitClient;
import com.example.crudretrofit.other_ui.CrudOperationActivity;
import com.example.crudretrofit.register.RegisterActivity;
import com.example.crudretrofit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText userEmailEdt;
    EditText userPasswordEdt;
    Button loginBtn;
    TextView signUpTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if(isAlreadyLogIn()){
            Intent intent=new Intent(LoginActivity.this, CrudOperationActivity.class);
            startActivity(intent);
            finish();

        }else {
            userEmailEdt = findViewById(R.id.userEmailEdt);
            userPasswordEdt = findViewById(R.id.userPasswordEdt);
            loginBtn = findViewById(R.id.loginBtn);
            signUpTv = findViewById(R.id.signUpTv);
            signUpTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userEmail = userEmailEdt.getText().toString();
                    String userPassword = userPasswordEdt.getText().toString();

                    ApiInterface apiInterface = RetrofitClient.getInstance().getApiInterface();
                    LoginRequest loginRequest = new LoginRequest(userEmail, userPassword);
                    LoginUser loginUser=new LoginUser();

                    // Call the login API
                    Call<LoginResponse> call = apiInterface.loginUser(loginRequest);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()&&response.body()!=null) {

                                LoginResponse loginResponse = response.body();
                                if (loginResponse.isStatus()) {
                                    //LoginUser loginUser = loginResponse.getUser();
                                    System.out.println("Login Response: " + loginResponse.getMessage());
                                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    saveLoginInfo(userEmail, userPassword);

                                    Intent intent = new Intent(LoginActivity.this, CrudOperationActivity.class);
                                      //intent.putExtra("userName",loginResponse.getUser().getName());
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }
    public void saveLoginInfo(String email,String password){
        SharedPreferences sharedPreferences=getSharedPreferences(Constants.My_Pref,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putBoolean(Constants.Already_Logged_In,true);
        editor.apply();
    }
    public boolean isAlreadyLogIn(){
        SharedPreferences sharedPreferences=getSharedPreferences(Constants.My_Pref,MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.Already_Logged_In,false);
    }
}