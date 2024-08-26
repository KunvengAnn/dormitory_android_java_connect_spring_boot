package com.example.dormitory_ui.pages;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dormitory_ui.R;
import com.example.dormitory_ui.components.Msg;
import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterRoom1 extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextViewGender;

    private ImageView RoomBtnBack;

    private EditText EtStudentName;
    private EditText EtDateOfBirth;
    private EditText EtEmail;
    private EditText EtStudent_id;
    private EditText EtDepartmentOfStudent;
    private EditText EtClassOfStudent;
    private EditText EtDescriptionSelf;
    private EditText EtContractStartDate;
    private EditText EtContractEndDate;
    private EditText EtNationality;
    private EditText EtPlaceOfBirth;
    private EditText EtCitizenIdentification;
    private EditText EtPhoneNumber;

    private Button btnContinue;

    //private S

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_room_1);

        autoCompleteTextViewGender = findViewById(R.id.idAutoCompleteTextView_gender);
        RoomBtnBack = findViewById(R.id.id_roomBackBtn);

        EtStudentName = findViewById(R.id.id_studentName);
        EtStudent_id = findViewById(R.id.idEditText_student_id);
        EtDateOfBirth = findViewById(R.id.idEditText_date_of_birth);
        EtDepartmentOfStudent = findViewById(R.id.id_departmentOfStudent);
        EtPhoneNumber = findViewById(R.id.id_phoneNumber);
        EtClassOfStudent = findViewById(R.id.id_classOfStudent);
        EtDescriptionSelf = findViewById(R.id.id_descriptionSelf);
        EtContractStartDate = findViewById(R.id.id_contract_start_date);
        EtContractEndDate = findViewById(R.id.id_contract_end_date);
        EtNationality = findViewById(R.id.id_nationality);
        EtPlaceOfBirth = findViewById(R.id.id_placeOfBirth);
        EtCitizenIdentification = findViewById(R.id.id_citizenIdentification);
        EtEmail = findViewById(R.id.id_email);

        EtEmail.setText("Vengann@tnut.edu.vn");

        btnContinue = findViewById(R.id.id_btnContinue);

        EditTextShowDate();
        setupGenderDropdown();
        setupBackButton();

        setUpContractDateStartAndContractDateEnd();

        BtnContinue();
    }
    // end of OnCreate

    private void BtnContinue() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Validate()) {
                    return;
                }

                Student student = new Student();
                student.setStudentName(EtStudentName.getText().toString().trim());
                student.setDateOfBirthStudent(EtDateOfBirth.getText().toString().trim());
                student.setStudentClass(EtClassOfStudent.getText().toString().trim());
                student.setDepartmentOfStudent(EtDepartmentOfStudent.getText().toString().trim());
                student.setStudentPhone(EtPhoneNumber.getText().toString().trim());
                student.setCitizenIdentification(EtCitizenIdentification.getText().toString().trim());
                student.setNationality(EtNationality.getText().toString().trim());
                student.setBirthPlace(EtPlaceOfBirth.getText().toString().trim());
                student.setDescriptionSelf(EtDescriptionSelf.getText().toString().trim());
                student.setStudentImageUrl("");
                student.setIdStudent(EtStudent_id.getText().toString().trim());

                String gender = autoCompleteTextViewGender.getText().toString().trim();
                boolean isMale;
                if ("Nam".equalsIgnoreCase(gender)) {
                    isMale = true;
                } else if ("Nữ".equalsIgnoreCase(gender)) {
                    isMale = false;
                } else {
                    isMale = false;
                }
                student.setStudentSex(isMale);

                Intent intent = new Intent(RegisterRoom1.this, RegisterRoom2.class);
                String studentJson = new Gson().toJson(student);

                String contractStartDate = EtContractStartDate.getText().toString().trim();
                String contractEndDate = EtContractEndDate.getText().toString().trim();

                intent.putExtra(Constants.STUDENT, studentJson);
                intent.putExtra(Constants.CONTRACT_START_DATE, contractStartDate);
                intent.putExtra(Constants.CONTRACT_END_DATE, contractEndDate);
                startActivity(intent);
            }
        });
    }

    private boolean Validate() {
        String emailPattern = "^[\\w._%+-]+@tnut\\.edu\\.vn$";

        if (EtStudentName.getText().toString().trim().isEmpty()) {
            Msg.showMessage(RegisterRoom1.this, "Vui lòng nhập tên sinh viên");
            return false;
        } else if (EtDateOfBirth.getText().toString().trim().isEmpty()) {
            Msg.showMessage(RegisterRoom1.this, "Vui lòng nhập ngày sinh");
            return false;
        } else if (EtEmail.getText().toString().trim().isEmpty()) {
            Msg.showMessage(RegisterRoom1.this, "Vui lòng nhập email");
            return false;
        } else if (!EtEmail.getText().toString().trim().matches(emailPattern)) {
            Msg.showMessage(RegisterRoom1.this, "Email không hợp lệ. Vui lòng nhập địa chỉ email @tnut.edu.vn");
            return false;
        } else if (EtStudent_id.getText().toString().trim().isEmpty()) {
            Msg.showMessage(RegisterRoom1.this, "Vui lòng nhập mã sinh viên");
            return false;
        } else if (EtDepartmentOfStudent.getText().toString().trim().isEmpty()) {
            Msg.showMessage(RegisterRoom1.this, "Vui lòng nhập khoa");
            return false;
        } else if (EtClassOfStudent.getText().toString().trim().isEmpty()) {
            Msg.showMessage(RegisterRoom1.this, "Vui lòng nhập lớp");
            return false;
        } else if (EtPhoneNumber.getText().toString().trim().isEmpty()) {
            Msg.showMessage(RegisterRoom1.this, "Vui lòng nhập số điện thoại");
            return false;
        } else if (EtNationality.getText().toString().trim().isEmpty()) {
            Msg.showMessage(RegisterRoom1.this, "Vui lòng nhập quốc tịch");
            return false;
        } else if (EtCitizenIdentification.getText().toString().trim().isEmpty()) {
            Msg.showMessage(RegisterRoom1.this, "Vui lòng nhập số CMND/CCCD");
            return false;
        }
        return true;
    }

    private void setupBackButton() {
        RoomBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpContractDateStartAndContractDateEnd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        // Set contract start date
        String startDate = sdf.format(calendar.getTime());
        EtContractStartDate.setText(startDate);

        // Calculate contract end date (6 months later)
        calendar.add(Calendar.MONTH, 6);
        String endDate = sdf.format(calendar.getTime());
        EtContractEndDate.setText(endDate);
    }

    private void setupGenderDropdown() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextViewGender.setAdapter(adapter);

        autoCompleteTextViewGender.setText(adapter.getItem(0).toString(), false);
    }

    private void EditTextShowDate() {
        EtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }


    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year1, monthOfYear, dayOfMonth);

                    // Format the date as yyyy-MM-dd
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = sdf.format(selectedDate.getTime());

                    // Set the formatted date to the EditText
                    EtDateOfBirth.setText(formattedDate);
                }, year, month, day);

        // Set minimum date to current date
        // datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
}
