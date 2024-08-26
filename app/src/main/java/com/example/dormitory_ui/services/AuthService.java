package com.example.dormitory_ui.services;

import com.example.dormitory_ui.models.UserModel;
import com.google.gson.JsonObject;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AuthService {
    @POST("auth/register")
    Call<UserModel> setRegister(@Body RequestBody requestBody);

    @POST("auth/login")
    Call<UserModel> setLogin(@Body RequestBody requestBody);

    @POST("auth/logout")
    Call<RequestBody> logout(@Body RequestBody requestBody);

    @POST("auth/introspect")
    Call<JsonObject> checkTokenValidity(@Body RequestBody requestBody);

    @POST("auth/getCurrentUser")
    Call<UserModel> getCurrentUser(@Body RequestBody requestBody);

    @PUT("users/user-update-id-student")
    Call<UserModel> updateUserWithIdStudent(@Body RequestBody requestBody);

}
