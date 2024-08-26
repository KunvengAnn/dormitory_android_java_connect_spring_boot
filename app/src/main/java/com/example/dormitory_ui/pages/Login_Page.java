package com.example.dormitory_ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.dormitory_ui.Exceptions.AuthException;
import com.example.dormitory_ui.R;
import com.example.dormitory_ui.components.Msg;
import com.example.dormitory_ui.controllers.AuthController;
import com.example.dormitory_ui.helper.SharePref;
import com.example.dormitory_ui.models.UserModel;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;

public class Login_Page extends AppCompatActivity {
    private TextView textView_register;
    private Button btnLogin;
    private EditText editText_email;
    private EditText editText_password;
    private CheckBox checkBox_remember;

    private Boolean isRemember = false;
    private AuthController authController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        // Force light mode close dark mode default
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        btnLogin = findViewById(R.id.id_btn_login);
        editText_email = findViewById(R.id.id_Email);
        editText_password = findViewById(R.id.id_password);
        textView_register = findViewById(R.id.id_text_register);

        checkBox_remember = findViewById(R.id.id_CheckBoxRemember);
        authController = new AuthController(this);

        btnTextRegister();
        btnLogin();
        checkRemember();
        getRemember();
    }
    // end of onCreate

    private void checkLogin(String email, String password) {
        authController.checkLogin(email, password, new AuthException() {
            @Override
            public void onSuccess(UserModel user) {
                if (isRemember) {
                    saveRemember(email, password);
                }else{
                    SharePref.getInstance(Login_Page.this).remove(Constants.email);
                    SharePref.getInstance(Login_Page.this).putString(Constants.email,editText_email.getText().toString().trim());
                }
                Intent intent = new Intent(Login_Page.this, HomePage.class);

                String userJS = new Gson().toJson(user);
                intent.putExtra(Constants.USERJSON, userJS);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Msg.showMessage(Login_Page.this, errorMessage);
            }
        });
    }

    private void btnLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText_email.getText().toString().trim();
                String password = editText_password.getText().toString().trim();

                if (email.isEmpty()) {
                    Msg.showMessage(Login_Page.this, "Hãy Nhập Email !");
                } else if (password.isEmpty()) {
                    Msg.showMessage(Login_Page.this, "Hãy Nhập Mật Khẩu !");
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Msg.showMessage(Login_Page.this, "Invalid email format");
                } else {
                    checkLogin(email, password);
                }
            }
        });
    }

    private void getRemember() {
        editText_email.setText(SharePref.getInstance(Login_Page.this).getString(Constants.email, ""));
        editText_password.setText(SharePref.getInstance(Login_Page.this).getString(Constants.password, ""));
    }

    private void checkRemember() {
        checkBox_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isRemember = true;
                } else {
                    isRemember = false;
                }
            }
        });
    }

    private void saveRemember(String email, String password) {

        SharePref.getInstance(Login_Page.this).remove(Constants.email);
        SharePref.getInstance(Login_Page.this).remove(Constants.password);

        SharePref.getInstance(Login_Page.this).putString(Constants.email, email);
        SharePref.getInstance(Login_Page.this).putString(Constants.password, password);
    }

    private void btnTextRegister() {
        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Page.this, Register_Page.class);
                startActivity(intent);
            }
        });
    }
}
