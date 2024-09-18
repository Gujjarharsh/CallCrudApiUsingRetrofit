package com.example.crudretrofit.register;

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
import com.example.crudretrofit.login.login_ui.LoginActivity;


public class RegisterActivity extends AppCompatActivity {
    EditText adminNameEdt;
    EditText adminPhoneEdt;
    EditText adminEmailEdt;
    EditText adminPasswordEdt;
    EditText adminConfirmPasswordEdt;
    Button registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        adminNameEdt=findViewById(R.id.adminNameEdt);
        adminPhoneEdt=findViewById(R.id.adminPhoneEdt);
        adminEmailEdt=findViewById(R.id.adminEmailEdt);
        adminPasswordEdt=findViewById(R.id.adminPasswordEdt);
        adminConfirmPasswordEdt=findViewById(R.id.adminConfirmPasswordEdt);
        registerBtn=findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                String adminName = adminNameEdt.getText().toString();
                String adminPhone = adminPhoneEdt.getText().toString();
                String adminEmail = adminEmailEdt.getText().toString();
                String adminPassword = adminPasswordEdt.getText().toString();
                String adminConfirmPassword = adminConfirmPasswordEdt.getText().toString();
                if (!adminPassword.equals(adminConfirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Confirm password and password must be same", Toast.LENGTH_SHORT).show();

                } else {

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    Toast.makeText(RegisterActivity.this, "sign up successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();


                }
            }
        });

    }

}