package com.example.dormitory_ui.services;

import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.models.StudentDTO;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface StudentService {
    @GET("student/{id}")
    Call<Student> getStudentById(@Path("id") String id_student);

    @Multipart
    @POST("student")
    Call<StudentDTO> addStudent(
            @Part("studentDTO") RequestBody studentDTO
    );

    @Multipart
    @POST("student/uploadImage")
    Call<ResponseBody> addImageWithStudentData(
            @Part MultipartBody.Part image,
            @Part("studentDTO") RequestBody requestBody
    );
}
