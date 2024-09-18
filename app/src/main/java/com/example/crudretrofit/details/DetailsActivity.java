package com.example.crudretrofit.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudretrofit.R;
import com.example.crudretrofit.user_list.userlist_models.GetUsersByIdResponse;
import com.example.crudretrofit.user.user_models.UserRequest;
import com.example.crudretrofit.retrofit.ApiInterface;
import com.example.crudretrofit.retrofit.RetrofitClient;
import com.example.crudretrofit.user.create_ui.UserCreateActivity;
import com.example.crudretrofit.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsActivity extends AppCompatActivity {
    ImageView editImage;
    ImageView userImage;
    TextView userNameTv;
    TextView userPhoneTv;
    TextView userEmailTv;
    TextView userAddressTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
    }
    protected void onResume() {
        super.onResume();
        userNameTv = findViewById(R.id.userNameTv);
        userPhoneTv = findViewById(R.id.userPhoneTv);
        userEmailTv = findViewById(R.id.userEmailTv);
        editImage = findViewById(R.id.editImage);
        userImage = findViewById(R.id.userImage);
        userAddressTv = findViewById(R.id.userAddressTv);
        Intent intent = getIntent();


        ApiInterface apiInterface = RetrofitClient.getInstance().getApiInterface();
        int userId = intent.getIntExtra(Constants.Id,-1);
        Call<GetUsersByIdResponse> call = apiInterface.getItemById(userId);

        call.enqueue(new Callback<GetUsersByIdResponse>() {

            @Override
            public void onResponse(@NonNull Call<GetUsersByIdResponse> call, @NonNull Response<GetUsersByIdResponse> response) {

                if (response.isSuccessful()) {
                    // Successfully fetched user details
                    GetUsersByIdResponse studentByIdResponse = response.body();
                    UserRequest user=studentByIdResponse.getUser();
                    if (user != null) {

                        userNameTv.setText(user.getName());
                        userPhoneTv.setText(user.getPhone());
                        userEmailTv.setText(user.getEmail());
                        userAddressTv.setText(user.getAddress());
                        editImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent edtIntent = new Intent(DetailsActivity.this, UserCreateActivity.class);
                                edtIntent.putExtra("Id", user.getId());
                                startActivity(edtIntent);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUsersByIdResponse> call, Throwable t) {

            }

        });
    }
}