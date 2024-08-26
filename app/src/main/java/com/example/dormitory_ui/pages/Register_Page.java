package com.example.dormitory_ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dormitory_ui.Exceptions.AuthException;
import com.example.dormitory_ui.R;
import com.example.dormitory_ui.components.Msg;
import com.example.dormitory_ui.controllers.AuthController;
import com.example.dormitory_ui.models.UserModel;

public class Register_Page extends AppCompatActivity {
    private TextView btnTextLogin;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button btnRegister;

    private AuthController authController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        authController = new AuthController(this);

        // Force light mode close dark mode default
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        BtnTextLogin();
        BtnRegister();

    }
    // end of onCreate

    private void BtnRegister() {
        btnRegister.setOnClickListener(v -> registration());
    }

    private void registration() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        boolean isCorrectCompare = comparePasswordAndConfirmPass();
        if (!isCorrectCompare) {
            return;
        }

        authController.registration(firstName, lastName, email, password, new AuthException() {
            @Override
            public void onSuccess(UserModel user) {
                Intent intent = new Intent(Register_Page.this, Login_Page.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Msg.showMessage(Register_Page.this, errorMessage);
            }
        });
    }

    private boolean comparePasswordAndConfirmPass() {
        String pass = editTextPassword.getText().toString().trim();
        String ConfirmPass = editTextConfirmPassword.getText().toString().trim();
        if (!pass.equals(ConfirmPass)) {
            Msg.showMessage(Register_Page.this, "Mật khẩu không trùng khớp");
            return false;
        }

        return true;
    }

    private void BtnTextLogin() {
        btnTextLogin = findViewById(R.id.id_textLogin);

        btnTextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Page.this, Login_Page.class);
                startActivity(intent);
            }
        });
    }
}
