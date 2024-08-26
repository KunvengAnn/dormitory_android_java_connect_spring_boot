package com.example.dormitory_ui.controllers;

import android.content.Context;
import android.util.Log;

import com.example.dormitory_ui.Exceptions.StudentException;
import com.example.dormitory_ui.helper.SharePref;
import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.models.StudentDTO;
import com.example.dormitory_ui.services.StudentService;
import com.example.dormitory_ui.services.ApiClient;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentController {
    private final Context context;
    private final String authToken;

    public StudentController(Context context) {
        this.context = context;
        authToken = SharePref.getInstance(context).getString(Constants.token, "");
    }

    public void getStudentById(String id_student,StudentException studentException) {
        StudentService studentService = ApiClient.getClientWithBearerToken(authToken).create(StudentService.class);
        Call<Student> call =studentService.getStudentById(id_student);

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Log.d("StudentController", "Student added successfully");

                    studentException.onSuccessTwo(response.body());
                } else {
                    Log.d("StudentController", "Failed to add student: " + response.message() + " " + response.code() + response.body());

                    studentException.onError("Failed to add student: " + response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                studentException.onError(t.getMessage());
            }
        });
    }

    public void addStudent(Student student, StudentException studentException) {
        Gson gson = new GsonBuilder().create();
        String studentJson = gson.toJson(student);

        RequestBody studentDTOJson = RequestBody.create(MediaType.parse("multipart/form-data"), studentJson);

        StudentService service = ApiClient.getClientWithBearerToken(authToken).create(StudentService.class);

        Call<StudentDTO> call = service.addStudent(studentDTOJson);
        call.enqueue(new Callback<StudentDTO>() {
            @Override
            public void onResponse(Call<StudentDTO> call, Response<StudentDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("StudentController", "Student added successfully");
                    studentException.onSuccess(response.body());
                } else {
                    Log.d("StudentController", "Failed to add student: " + response.message() + " " + response.code() + response.body());

                    studentException.onError("Failed to add student: " + response.message() + " " + response.code());
                }
            }

            @Override
            public void onFailure(Call<StudentDTO> call, Throwable t) {
                Log.d("StudentController", "Error onFailure: " + t.getMessage());

                studentException.onError(t.getMessage());
            }
        });
    }

    public void uploadImageWithStudent(File imageFile, StudentDTO studentDTO) {
        StudentService studentService = ApiClient.getClientWithBearerToken(authToken).create(StudentService.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

        Gson gson = new GsonBuilder().create();
        String studentJson = gson.toJson(studentDTO);
        RequestBody studentDTOJson = RequestBody.create(MediaType.parse("application/json"), studentJson);

        Call<ResponseBody> call = studentService.addImageWithStudentData(body, studentDTOJson);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBodyString = response.body().string();
                        Log.d("uploadImage", "onResponse: ok " + response.message() + " - " + responseBodyString);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("uploadImage", "Error reading response body", e);
                    }
                } else {
                    try {
                        String errorBodyString = response.errorBody().string();
                        Log.d("uploadImage", "onResponse: " + response.message() + " - " + errorBodyString);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("uploadImage", "Error reading error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("uploadImage", "onResponse: onFailure " + t.getMessage());
            }
        });
    }

    public void deleteStudent() {

    }

    public void updateStudent() {

    }

}
