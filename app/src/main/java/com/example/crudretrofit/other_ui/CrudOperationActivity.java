package com.example.crudretrofit.other_ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crudretrofit.R;
import com.example.crudretrofit.login.login_ui.LoginActivity;
import com.example.crudretrofit.user.create_ui.UserCreateActivity;
import com.example.crudretrofit.user_list.ui.UserListActivity;
import com.example.crudretrofit.utils.Constants;

public class CrudOperationActivity extends AppCompatActivity {
TextView addUsersTv;
TextView userListTv,userLogOutTv,loginUserNameTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud_operation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addUsersTv=findViewById(R.id.addUsersTv);
        userListTv=findViewById(R.id.userListTv);
        userLogOutTv=findViewById(R.id.logOutBtn);
        loginUserNameTv=findViewById(R.id.loginUserName);
       Intent intent=getIntent();
       String loginUserName=intent.getStringExtra("userName");
        loginUserNameTv.setText(loginUserName);

        addUsersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CrudOperationActivity.this, UserCreateActivity.class);
                startActivity(intent);
                finish();
            }
        });
        userListTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CrudOperationActivity.this, UserListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        userLogOutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences(Constants.My_Pref,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent=new Intent(CrudOperationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}