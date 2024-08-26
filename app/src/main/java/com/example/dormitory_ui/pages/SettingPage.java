package com.example.dormitory_ui.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dormitory_ui.Exceptions.RoomException;
import com.example.dormitory_ui.R;
import com.example.dormitory_ui.components.LoadingDialog;
import com.example.dormitory_ui.components.Msg;
import com.example.dormitory_ui.controllers.RoomController;
import com.example.dormitory_ui.models.Room1;
import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

public class SettingPage extends AppCompatActivity {
    private ImageView SettingBtnBack;

    private TextView tvStName;
    private TextView tvStGender;
    private TextView tvEmail;
    private TextView tvStBirthOfDate;
    private TextView tvStPlaceOfBirth;
    private TextView tvStId;
    private TextView tvStPhoneNumber;
    private TextView tvStCitizenIdentification;
    private TextView tvDtNumber;
    private TextView tvRoomNumber;
    private TextView tvStatusRoom;
    private TextView tvDateStarRegister;
    private TextView tvNationality;
    private TextView tvDateEndOfRegisterRoom;

    private RoomController roomController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);

        SettingBtnBack = findViewById(R.id.id_SettingBackBtn);
        tvStName = findViewById(R.id.id_stName);
        tvStGender = findViewById(R.id.stGender);
        tvStBirthOfDate = findViewById(R.id.stBirthOfDate);
        tvStId = findViewById(R.id.stId);
        tvStPhoneNumber = findViewById(R.id.stPhoneNumber);
        tvEmail = findViewById(R.id.stEmail);
        tvStCitizenIdentification = findViewById(R.id.stCitizenIdentification);
        tvStPlaceOfBirth = findViewById(R.id.stPlaceOfBirth);
        tvDtNumber = findViewById(R.id.dtNumber);
        tvRoomNumber = findViewById(R.id.roomNumber);
        tvStatusRoom = findViewById(R.id.statusRoom);
        tvDateStarRegister = findViewById(R.id.dateStarRegister);
        tvNationality = findViewById(R.id.stNationality);
        tvDateEndOfRegisterRoom = findViewById(R.id.dateEndRegister);

        roomController = new RoomController(this);

        BtnBack();
        setUpForThisPage();
        getImportData();
    }
    // end of OnCreate

    private void BtnBack() {
        SettingBtnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void setUpForThisPage() {

    }

    private void getImportData() {
        Intent intent = getIntent();

        if (intent != null) {
            String studentJson = intent.getStringExtra(Constants.STUDENT);
            String userJson = intent.getStringExtra(Constants.USERJSON);

            if (studentJson != null && !studentJson.isEmpty()) {
                Student student = new Gson().fromJson(studentJson, Student.class);
                if (student != null) {

                    String gender = "";
                    if (student.getStudentSex()) {
                        gender = "Nam";
                    } else {
                        gender = "Nữ";

                    }
                    tvStName.setText(student.getStudentName());
                    tvStGender.setText(gender);
                    tvStBirthOfDate.setText(student.getBirthPlace());
                    tvStId.setText(student.getIdStudent());
                    tvStPhoneNumber.setText(student.getStudentPhone());
                    tvStCitizenIdentification.setText(student.getCitizenIdentification());
                    tvStPlaceOfBirth.setText(student.getBirthPlace());
                    tvEmail.setText(student.getStudent_email());
                    tvNationality.setText(student.getNationality());

                    tvDateStarRegister.setText(student.getContracts().get(0).getDateStartContract());
                    tvDateEndOfRegisterRoom.setText(student.getContracts().get(0).getDateEndContract());

                    getRoomById(student.getContracts().get(0).getIdRoom());
                }
            }
        }
    }

    //
    private void getRoomById(Integer id_room) {
        roomController.getRoomById(id_room, new RoomException() {
            @Override
            public void Success(List<Room1> room1Ls) {

            }

            @Override
            public void onSuccess(Room1 room) {
                Log.d("ONRoom", "onSuccess: roomId" + room.getIdRoom());
                tvDtNumber.setText(room.getDormitory1().getDormitory1Name());
                tvRoomNumber.setText(String.valueOf(room.getRoomNumber()));
                String statusStr = "";
                if (room.isStatusIsEmptyRoom()) {
                    statusStr = "ko có người";
                } else {
                    statusStr = "đang có người";
                }
                tvStatusRoom.setText(statusStr);
            }

            @Override
            public void onSuccessTwo(JsonObject jsonObject) {

            }

            @Override
            public void Error(String msg) {
                Msg.showMessage(SettingPage.this, msg);
            }
        });
    }

    private void showLoading() {
        LoadingDialog.showCustomLoadingDialog(SettingPage.this);
    }

    private void DisableLoading() {
        LoadingDialog.dismissCustomLoadingDialog();
    }
}
