package com.example.dormitory_ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dormitory_ui.R;

public class Main_login_register_page extends AppCompatActivity {

    private TextView btnRegister;
    private TextView btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_register);

        BtnLoginAndRegister();
    }
    // end of onCreate

    private void BtnLoginAndRegister(){
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_login_register_page.this, Login_Page.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_login_register_page.this, Register_Page.class);
                startActivity(intent);
            }
        });
    }
}
