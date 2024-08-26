package com.example.dormitory_ui.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dormitory_ui.Exceptions.AuthException;
import com.example.dormitory_ui.Exceptions.StudentException;
import com.example.dormitory_ui.Exceptions.TokenValidity;
import com.example.dormitory_ui.R;
import com.example.dormitory_ui.components.Dialogs;
import com.example.dormitory_ui.components.LoadingDialog;
import com.example.dormitory_ui.components.Msg;
import com.example.dormitory_ui.controllers.AuthController;
import com.example.dormitory_ui.controllers.StudentController;
import com.example.dormitory_ui.helper.SharePref;
import com.example.dormitory_ui.models.Contract;
import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.models.StudentDTO;
import com.example.dormitory_ui.models.UserModel;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomePage extends AppCompatActivity {
    private Button btnLogOut;
    private TextView textView_userName;

    private Button btnRegisterRoom;
    private Button btnSetting;
    private Button btnNotification;
    private Button btnCheckOutRoom;
    private Button btnRoomChange;
    private Button btnPayment;
    private Button btnViewCost;
    private Button btnRepairRoom;

    private boolean isPermissionBtnRegisterRoom = false;

    private AuthController authController;
    private StudentController studentController;

    private UserModel userG = new UserModel();
    private Student st = new Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        btnRegisterRoom = findViewById(R.id.id_btnRegisterRoom);
        btnLogOut = findViewById(R.id.id_btnLogout);
        btnSetting = findViewById(R.id.idBtnSetting);
        btnNotification = findViewById(R.id.BtnNotifica);
        btnCheckOutRoom = findViewById(R.id.BtnCheckOutRoom);

        btnRoomChange = findViewById(R.id.BtnRoomChange);
        btnPayment = findViewById(R.id.BtnPayment);
        btnViewCost = findViewById(R.id.BtnView_cost);
        btnRepairRoom = findViewById(R.id.BtnRepairRoom);

        authController = new AuthController(this);
        studentController = new StudentController(this);
        showLoading();
        checkTokenValidity();
        btnRegisterRoom();
        btnLogOut();
        getInfoFromIntent();
        AllBtn();
    }
    //end of onCreate

    //
    private void AllBtn() {
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userG.getId_student() == null) {
                    Msg.showMessage(HomePage.this, "Hãy đăng ký phòng đã nhé!");
                } else {
                    Intent intent = new Intent(HomePage.this, SettingPage.class);
                    intent.putExtra(Constants.STUDENT, new Gson().toJson(st));
                    intent.putExtra(Constants.USERJSON, new Gson().toJson(userG));
                    startActivity(intent);
                }
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userG.getId_student() == null) {
                    Msg.showMessage(HomePage.this, "Hãy đăng ký phòng đã nhé!");
                } else {

                }
            }
        });

        btnCheckOutRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userG.getId_student() == null) {
                    Msg.showMessage(HomePage.this, "Hãy đăng ký phòng đã nhé!");
                } else {

                }
            }
        });

        btnRoomChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userG.getId_student() == null) {
                    Msg.showMessage(HomePage.this, "Hãy đăng ký phòng đã nhé!");
                } else {

                }
            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userG.getId_student() == null) {
                    Msg.showMessage(HomePage.this, "Hãy đăng ký phòng đã nhé!");
                } else {

                }
            }
        });

        btnViewCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userG.getId_student() == null) {
                    Msg.showMessage(HomePage.this, "Hãy đăng ký phòng đã nhé!");
                } else {

                }
            }
        });

        btnRepairRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userG.getId_student() == null) {
                    Msg.showMessage(HomePage.this, "Hãy đăng ký phòng đã nhé!");
                } else {

                }
            }
        });
    }

    //
    private void getStudentData(String id_student) {
        studentController.getStudentById(id_student, new StudentException() {
            @Override
            public void onSuccess(StudentDTO studentDTO) {
            }

            @Override
            public void onSuccessTwo(Student student) {
                DisableLoading();
                st = student;

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date currentDate = new Date();

                for (Contract c : student.getContracts()) {
                    try {
                        Date startDate = dateFormat.parse(c.getDateStartContract());
                        Date endDate = dateFormat.parse(c.getDateEndContract());

                        if (currentDate.after(endDate)) {
                            isPermissionBtnRegisterRoom = true;
                            Log.d("success", "end date end: " + c.getDateEndContract());
                            Log.d("success", "Need Register room again !");
                        } else {
                            isPermissionBtnRegisterRoom = false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("error", "Date parsing failed: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                DisableLoading();
                Msg.showMessage(HomePage.this, errorMessage);
            }
        });
    }

    private void getCurrentUserByEmail() {
        authController.getCurrentUserByEmail(new AuthException() {
            @Override
            public void onSuccess(UserModel user) {
                DisableLoading();
                userG = user;
                setTextView_userName(user.getFirstName() + " " + user.getLastName());

                if (user.getId_student() != null) {
                    getStudentData(user.getId_student());
                } else {
                    isPermissionBtnRegisterRoom = true;
                }
            }

            @Override
            public void onError(String errorMessage) {
                DisableLoading();
                Msg.showMessage(HomePage.this, errorMessage);
            }
        });
    }

    private void btnRegisterRoom() {
        btnRegisterRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionBtnRegisterRoom) {
                    SharePref.getInstance(HomePage.this).remove(Constants.STUDENTID);
                    SharePref.getInstance(HomePage.this).putString(Constants.STUDENTID,userG.getId_student());
                    Intent intent = new Intent(HomePage.this, RegisterRoom1.class);
                    startActivity(intent);
                } else {
                    Msg.showMessage(HomePage.this, "Phòng Đã Dăng ky");
                }
            }
        });
    }

    private void checkTokenValidity() {
        String authToken = SharePref.getInstance(HomePage.this).getString(Constants.token,"");
        authController.checkTokenValidity(authToken, new TokenValidity() {
            @Override
            public void onSuccess(JsonObject jsonResponse) {
                JsonElement validElement = jsonResponse.get("valid");
                if (validElement != null && validElement.isJsonPrimitive() && validElement.getAsJsonPrimitive().isBoolean()) {
                    boolean isValid = validElement.getAsBoolean();

                    if (!isValid) {
                        Intent intent = new Intent(HomePage.this, Login_Page.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                Msg.showMessage(HomePage.this, "Error" + errorMessage);
            }
        });
    }

    private void getInfoFromIntent() {
        Intent intent = getIntent();
        String userJson = intent.getStringExtra(Constants.USERJSON);
        if (userJson != null && !userJson.isEmpty()) {
            Gson gson = new GsonBuilder().create();
            userG = gson.fromJson(userJson, UserModel.class);

            if (userG != null) {
                setTextView_userName(userG.getFirstName() + " " + userG.getLastName());

                if (userG.getId_student() != null) {
                    getStudentData(userG.getId_student());
                } else {
                    isPermissionBtnRegisterRoom = true;
                }
            }
        } else {
            getCurrentUserByEmail();
        }
    }

    private void setTextView_userName(String userName) {
        textView_userName = findViewById(R.id.id_username);
        textView_userName.setText(userName);
    }

    private void showLogoutDialog() {
        Dialogs.showConfirmationDialog(
                this,
                "Đăng xuất",
                "Bạn có muốn đăng xuất không?",
                "Có",
                "Không",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handleLogout();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
    }

    private void handleLogout() {
        SharePref.getInstance(HomePage.this).remove(Constants.token);

        Intent intent = new Intent(HomePage.this, Login_Page.class);
        startActivity(intent);
        finish();
    }

    private void btnLogOut() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
    }

    private void showLoading() {

        LoadingDialog.showCustomLoadingDialog(HomePage.this);

    }

    private void DisableLoading() {
        LoadingDialog.dismissCustomLoadingDialog();
    }
}

//        File imageFile = drawableToFile(R.drawable.home);
//        StudentDTO DTO = new StudentDTO();
//        DTO.setIdStudent("123");
//        DTO.setStudentName("John Doe");
//        DTO.setStudentSex(true);
//        DTO.setDateOfBirthStudent("2000-05-15");
//        DTO.setStudentClass("10A");
//        DTO.setDepartmentOfStudent("Science");
//        DTO.setStudentPhone("+1234567890");
//        DTO.setCitizenIdentification("AB123456789");
//
//        studentController.uploadImage(imageFile,DTO);
//////////////////////////////////////////////////////////////


//    private File drawableToFile(int drawableResId) {
//        // Get drawable
//        Drawable drawable = ContextCompat.getDrawable(this, drawableResId);
//        Bitmap bitmap = drawableToBitmap(drawable);
//
//        // Create a file in the external storage directory
//        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image_to_upload.png");
//
//        try (FileOutputStream out = new FileOutputStream(file)) {
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // Save bitmap to file
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return file;
//    }
//
//    private Bitmap drawableToBitmap(Drawable drawable) {
//        if (drawable instanceof BitmapDrawable) {
//            return ((BitmapDrawable) drawable).getBitmap();
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        drawable.draw(canvas);
//        return bitmap;
//    }
