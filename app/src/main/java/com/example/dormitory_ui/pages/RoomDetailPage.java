package com.example.dormitory_ui.pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dormitory_ui.Exceptions.AuthException;
import com.example.dormitory_ui.Exceptions.ContractException;
import com.example.dormitory_ui.Exceptions.RoomException;
import com.example.dormitory_ui.Exceptions.StudentException;
import com.example.dormitory_ui.R;
import com.example.dormitory_ui.components.AdapterRecycler1;
import com.example.dormitory_ui.components.LoadingDialog;
import com.example.dormitory_ui.components.Msg;
import com.example.dormitory_ui.controllers.AuthController;
import com.example.dormitory_ui.controllers.ContractController;
import com.example.dormitory_ui.controllers.RoomController;
import com.example.dormitory_ui.controllers.StudentController;
import com.example.dormitory_ui.helper.SharePref;
import com.example.dormitory_ui.models.Contract;
import com.example.dormitory_ui.models.Contract1;
import com.example.dormitory_ui.models.Room;
import com.example.dormitory_ui.models.Room1;
import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.models.StudentDTO;
import com.example.dormitory_ui.models.UserModel;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RoomDetailPage extends AppCompatActivity {
    private ImageView btnBackToRegisterRoom;
    private Button BtnRegisterRoom;
    private TextView tvRoomPrice;
    private TextView tvQuantityAvailableInRoom;

    private RecyclerView recyclerViewDetails;
    private AdapterRecycler1 adapterRecycler1;

    private RoomController roomController;
    private StudentController studentController;
    private ContractController contractController;
    private AuthController authController;

    boolean registerBtnClicked = false;
    int quantityAvailableInRoom = 0;
    boolean isBoy = false;
    String studentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_room_detail);

        btnBackToRegisterRoom = findViewById(R.id.id_BtnBackToRegisterRoom);
        BtnRegisterRoom = findViewById(R.id.BtnRegisterRoom);
        tvRoomPrice = findViewById(R.id.id_roomPrice);
        tvQuantityAvailableInRoom = findViewById(R.id.id_quantityAvailableInRoom);

        roomController = new RoomController(this);
        studentController = new StudentController(this);
        contractController = new ContractController(this);
        authController = new AuthController(this);

        recyclerViewDetails = findViewById(R.id.id_recycler);
        recyclerViewDetails.setLayoutManager(new LinearLayoutManager(this));
        adapterRecycler1 = new AdapterRecycler1(this, new ArrayList<>());
        recyclerViewDetails.setAdapter(adapterRecycler1);

        studentId = SharePref.getInstance(RoomDetailPage.this).getString(Constants.STUDENTID, "");

        getDataFromIntent();
        setBtnBackToRegisterRoom();
        setBtnRegisterRoom();
    }
    //end of onCreate

    private void setBtnBackToRegisterRoom() {
        btnBackToRegisterRoom.setOnClickListener(v -> {
            finish();
        });
    }

    private void setBtnRegisterRoom() {
        BtnRegisterRoom.setOnClickListener(v -> {
            registerBtnClicked = true;
            getDataFromIntent();
        });
    }

    private void updateUserWithIdStudent(Student student, String email) {
        authController.updateUserWithIdStudent(student, email, new AuthException() {
            @Override
            public void onSuccess(UserModel user) {
                DisableLoading();
                isOkAll();
                Log.d("OnSuccess", "onSuccess update user ok");
            }

            @Override
            public void onError(String errorMessage) {
                DisableLoading();
                Msg.showMessage(RoomDetailPage.this, errorMessage);
            }
        });
    }

    public Date convertStringToDate(String dateString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Date date = null;
        try {
            if (dateString != null && !dateString.isEmpty()) {
                date = sdf.parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(Constants.INFO, "Date parsing error: " + e.getMessage());
        }
        return date;
    }

    @SuppressLint("SetTextI18n")
    private void getDataFromIntent() {
        Intent intent = getIntent();
        String roomJson = intent.getStringExtra(Constants.ROOMJSON);
        String studentJson = intent.getStringExtra(Constants.STUDENT);

        String contractStarStr = intent.getStringExtra(Constants.CONTRACT_START_DATE);
        String contractEndDateStr = intent.getStringExtra(Constants.CONTRACT_END_DATE);
        String dormitoryId = intent.getStringExtra(Constants.DORMITORYID);

        if (roomJson != null) {
            Gson gson = new Gson();
            Room room = gson.fromJson(roomJson, Room.class);

            tvRoomPrice.setText(String.valueOf(room.getRoom_price()) + " $");
            quantityAvailableInRoom = room.getRoom_max_capacity() - room.getQuantity_person();
            tvQuantityAvailableInRoom.setText("đang còn" + " " + String.valueOf(quantityAvailableInRoom) + " " + "Chỗ");

            getRoomById(room.getId_room());

            if (studentJson != null) {
                Student st = gson.fromJson(studentJson, Student.class);

                Log.d(Constants.INFO, "getDataFromIntent dormitoryId: " + dormitoryId);

                Contract ct = new Contract();
                ct.setIdRoom(room.getId_room());
                ct.setIdStudent(st.getIdStudent());

                ct.setDateStartContract(contractStarStr);
                ct.setDateEndContract(contractEndDateStr);
                ct.setIdDormitory(Integer.parseInt(dormitoryId));

                room.setId_dormitory(Integer.parseInt(dormitoryId));

                Integer maxInRoom = room.getRoom_max_capacity();
                Integer currentQuantity = room.getQuantity_person();

                if (currentQuantity == 0) {
                    if (st.getStudentSex()) {
                        isBoy = true;
                    }
                    room.setStatus_room_is_boy(isBoy);
                    room.setStatus_is_empty_room(false);
                } else if (currentQuantity >= 1) {
                    if (st.getStudentSex() != room.getStatus_room_is_boy()) {
                        Msg.showMessage(RoomDetailPage.this, "Giới tính khác nhau =>đổi phòng đi!");
                    } else {
                        room.setStatus_room_is_boy(room.getStatus_room_is_boy());
                    }
                }

                if (currentQuantity >= maxInRoom) {
                    Msg.showMessage(RoomDetailPage.this, "The room is already at full capacity. Cannot add more students.");
                } else {
                    room.setQuantity_person(currentQuantity + 1);

                    if (registerBtnClicked) {
                        if (studentId == null || studentId.isEmpty()) {
                            addDataToDB(st, ct, room);
                        }else{
                            addContract(ct);
                            UpdateRoomDB(room);
                        }
                    }
                }
            }
        }
    }

    private void getRoomById(Integer id_room) {
        roomController.getRoomById(id_room, new RoomException() {
            @Override
            public void Success(List<Room1> room1Ls) {
            }

            @Override
            public void onSuccess(Room1 room) {
                adapterRecycler1 = new AdapterRecycler1(RoomDetailPage.this, room.getContract1s());
                recyclerViewDetails.setAdapter(adapterRecycler1);

                for (Contract1 c : room.getContract1s()) {
                    Log.d(Constants.INFO, "getIdStudent: " + c.getStudentDTO().getIdStudent());
                    Log.d(Constants.INFO, "getStudentClass: " + c.getStudentDTO().getStudentClass());
                    Log.d(Constants.INFO, "getStudentName: " + c.getStudentDTO().getStudentName());
                    Log.d(Constants.INFO, "getNationality : " + c.getStudentDTO().getNationality());
                }
            }

            @Override
            public void onSuccessTwo(JsonObject jsonObject) {

            }

            @Override
            public void Error(String msg) {
                Msg.showMessage(RoomDetailPage.this, "Error room:" + msg);
            }
        });
    }

    //
    private void addContract(Contract contract) {
        contractController.addContract(contract, new ContractException() {
            @Override
            public void Success(List<Contract> contractLs) {
            }

            @Override
            public void onSuccess(Contract contract) {
                Log.d("onSuccess", "onResponse: addContract");
            }

            @Override
            public void onSuccessTwo(JsonObject jsonObject) {
            }

            @Override
            public void Error(String msg) {
                Msg.showMessage(RoomDetailPage.this, "Error contract:" + msg);
            }
        });
    }

    private void UpdateRoomDB(Room room) {
        // update room
        roomController.updateRoom(room.getId_room(), room, new RoomException() {
            @Override
            public void Success(List<Room1> room1Ls) {

            }

            @Override
            public void onSuccess(Room1 room1) {
                Log.d("onSuccess", "onResponse: UpdateRoomDB");
            }

            @Override
            public void onSuccessTwo(JsonObject jsonObject) {

            }

            @Override
            public void Error(String msg) {
                Msg.showMessage(RoomDetailPage.this, msg);
            }
        });
    }


    // add all to DB
    private void addDataToDB(Student student, Contract contract, Room room) {
        studentController.addStudent(student, new StudentException() {
            @Override
            public void onSuccess(StudentDTO studentDTO) {

                addContract(contract);
                UpdateRoomDB(room);

                String email = SharePref.getInstance(RoomDetailPage.this).getString(Constants.email, "");
                updateUserWithIdStudent(student, email);
            }

            @Override
            public void onSuccessTwo(Student student) {

            }

            @Override
            public void onError(String errorMessage) {
                DisableLoading();
                Msg.showMessage(RoomDetailPage.this, "Error student:" + errorMessage);
            }
        });
    }

    private void isOkAll() {
        Intent intent = new Intent(RoomDetailPage.this, HomePage.class);
        startActivity(intent);
    }
    //

    private void showLoading() {
        LoadingDialog.showCustomLoadingDialog(RoomDetailPage.this);
    }

    private void DisableLoading() {
        LoadingDialog.dismissCustomLoadingDialog();
    }
}
