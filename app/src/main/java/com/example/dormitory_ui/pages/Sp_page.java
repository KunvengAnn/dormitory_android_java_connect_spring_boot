package com.example.dormitory_ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.dormitory_ui.R;
import com.example.dormitory_ui.helper.SharePref;
import com.example.dormitory_ui.utils.Constants;

public class Sp_page extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_page);

        // Force light mode close dark mode default
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String hasToken = SharePref.getInstance(Sp_page.this).getString(Constants.token, "");

                if(!hasToken.isEmpty()){
                    Intent intent = new Intent(Sp_page.this,HomePage.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(Sp_page.this, Main_login_register_page.class);

                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
    // end of OnCreate

}
