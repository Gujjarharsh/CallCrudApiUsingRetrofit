package com.example.crudretrofit.user.create_ui;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.crudretrofit.R;
import com.example.crudretrofit.user_list.userlist_models.GetUsersByIdResponse;
import com.example.crudretrofit.user.user_models.UserResponse;
import com.example.crudretrofit.user.user_models.UserRequest;
import com.example.crudretrofit.retrofit.UserRepository;
import com.example.crudretrofit.other_ui.CrudOperationActivity;
import com.example.crudretrofit.utils.Constants;

public class UserCreateActivity extends AppCompatActivity {
    EditText userNameEdt;
    EditText userPhoneEdt;
    EditText userEmailEdt;
    EditText userPasswordEdt;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_create);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userNameEdt = findViewById(R.id.userNameEdt);
        userPhoneEdt = findViewById(R.id.userPhoneEdt);
        userEmailEdt = findViewById(R.id.userEmailEdt);
        userPasswordEdt = findViewById(R.id.userPasswordEdt);
        createBtn = findViewById(R.id.createBtn);
        UserRepository userRepository=new UserRepository();
        Intent intent = getIntent();
         int userId=intent.getIntExtra(Constants.Id,-1);
        if (userId >0) {
            createBtn.setText("Update");
            userRepository.getUserById(userId, new UserRepository.UserCallback<GetUsersByIdResponse>() {
                @Override
                public void onSuccess(GetUsersByIdResponse data) {
                    UserRequest user=data.getUser();
                    userNameEdt.setText(user.getName());
                        userPhoneEdt.setText(user.getPhone());
                        userEmailEdt.setText(user.getEmail());
                        userPasswordEdt.setText(user.getAddress());
                }
                @Override
                public void onError(String error) {
                }
            });
        }

            createBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String userName = userNameEdt.getText().toString();
                    String userPhone = userPhoneEdt.getText().toString();
                    String userEmail = userEmailEdt.getText().toString();
                    String userPassword = userPasswordEdt.getText().toString();
                    UserRequest user = new UserRequest();
                    user.setName(userName);
                    user.setPhone(userPhone);
                    user.setEmail(userEmail);
                    user.setAddress(userPassword);

                    if (userId >0) {

                        user.setId(userId);

                        userRepository.updateUser(user, new UserRepository.UserCallback<UserResponse>() {
                            @Override
                            public void onSuccess(UserResponse data) {
                                Toast.makeText(UserCreateActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                        return;
                    }


                    userRepository.createUser(user, new UserRepository.UserCallback<UserResponse>() {
                        @Override
                        public void onSuccess(UserResponse data) {
                            Toast.makeText(UserCreateActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });

                    Intent intent = new Intent(UserCreateActivity.this, CrudOperationActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

    }
}