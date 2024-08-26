package com.example.dormitory_ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dormitory_ui.Exceptions.DormitoryException;
import com.example.dormitory_ui.R;
import com.example.dormitory_ui.components.LoadingDialog;
import com.example.dormitory_ui.components.Msg;
import com.example.dormitory_ui.components.MyGridAdapter;
import com.example.dormitory_ui.controllers.DormitoryController;
import com.example.dormitory_ui.models.Dormitory;
import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RegisterRoom2 extends AppCompatActivity {
    private GridView gridView;
    private ImageView BtnBackToStudentRegister;


    private Student student;
    private String contractStartDate = "";
    private String contractEndDate = "";
    private DormitoryController dormitoryController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_room_2);

        gridView = findViewById(R.id.gridView);
        BtnBackToStudentRegister = findViewById(R.id.BtnBackToStudentRegister);

        dormitoryController = new DormitoryController(this);

        getAllDormitory();
        getStudentFromIntent();
        setBtnBackToStudentRegister();
    }
    // end ofOnCreate

    private void setBtnBackToStudentRegister() {
        BtnBackToStudentRegister.setOnClickListener(v -> {
            finish();
        });
    }

    private void getStudentFromIntent() {
        Intent intent = getIntent();
        String studentJson = intent.getStringExtra(Constants.STUDENT);
        contractStartDate = intent.getStringExtra(Constants.CONTRACT_START_DATE);
        contractEndDate = intent.getStringExtra(Constants.CONTRACT_END_DATE);

        if (studentJson != null) {
            Gson gson = new Gson();
            student = gson.fromJson(studentJson, Student.class);

            Log.d(Constants.INFO, "getStudentFromIntent: " + student.getStudentName());
        }
    }

    public void getAllDormitory() {
        showLoading();
        dormitoryController.getAllDormitoryData(new DormitoryException() {
            @Override
            public void success(List<Dormitory> dormitories) {
                DisableLoading();
                List<String> titleLs = new ArrayList<>();
                for (Dormitory dt : dormitories) {
                    titleLs.add(dt.getDormitory_name());
                }
                updateGridView(titleLs, dormitories);
            }

            @Override
            public void error(String errorMessage) {
                DisableLoading();
                Msg.showMessage(RegisterRoom2.this, errorMessage);
            }
        });
    }

    private void updateGridView(List<String> titleLs, List<Dormitory> dormitoryLs) {
        List<Integer> imageResIds = new ArrayList<>();
        int defaultImageResId = R.drawable.building;

        for (int i = 0; i < titleLs.size(); i++) {
            imageResIds.add(defaultImageResId);
        }

        MyGridAdapter adapter = new MyGridAdapter(this, titleLs, imageResIds, dormitoryLs, student,contractStartDate,contractEndDate);
        gridView.setAdapter(adapter);
    }

    private void showLoading() {
        LoadingDialog.showCustomLoadingDialog(RegisterRoom2.this);
    }

    private void DisableLoading() {
        LoadingDialog.dismissCustomLoadingDialog();
    }
}
