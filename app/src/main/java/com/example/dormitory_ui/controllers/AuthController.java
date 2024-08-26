package com.example.dormitory_ui.controllers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dormitory_ui.Exceptions.AuthException;
import com.example.dormitory_ui.Exceptions.TokenValidity;
import com.example.dormitory_ui.components.LoadingDialog;
import com.example.dormitory_ui.helper.SharePref;
import com.example.dormitory_ui.models.Student;
import com.example.dormitory_ui.models.UserModel;
import com.example.dormitory_ui.services.ApiClient;
import com.example.dormitory_ui.services.AuthService;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthController {

    private final Context context;
    private final String authToken;

    public AuthController(Context context) {
        this.context = context;
        this.authToken = SharePref.getInstance(context).getString(Constants.token, "");
    }

    private void showLoading() {
        LoadingDialog.showCustomLoadingDialog(context);
    }

    private void DisableLoading() {
        LoadingDialog.dismissCustomLoadingDialog();
    }

    public void checkLogin(String email, String password, AuthException authException) {
        showLoading();

        AuthService apiService = ApiClient.getClient().create(AuthService.class);

        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(password);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(userModel);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
        Call<UserModel> call = apiService.setLogin(requestBody);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                DisableLoading();
                if (response.isSuccessful() && response.body() != null) {
                    UserModel user = response.body();

                    SharePref.getInstance(context).remove(Constants.token);
                    SharePref.getInstance(context).putString(Constants.token, user.getToken());

                    if (authException != null) {
                        authException.onSuccess(user);
                    }
                } else {
                    DisableLoading();

                    authException.onError("Email or password is incorrect.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                DisableLoading();
                authException.onError(t.getMessage());
            }
        });
    }

    public void getCurrentUserByEmail(AuthException authException) {
        String email = SharePref.getInstance(context).getString(Constants.email, "");

        Log.d(Constants.INFO, "getCurrentUserByEmail: " + email);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);

        String json = new Gson().toJson(jsonObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        AuthService apiService = ApiClient.getClient().create(AuthService.class);
        Call<UserModel> call = apiService.getCurrentUser(requestBody);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserModel user = response.body();
                    if (authException != null) {
                        authException.onSuccess(user);
                    }
                } else {
                    if (authException != null) {
                        authException.onError("Error " + response.message() + response.code());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                if (authException != null) {
                    authException.onError(t.getMessage());
                }
            }
        });
    }

    //
    public void registration(String firstName, String lastName, String email, String password, AuthException authException) {
        showLoading();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", firstName);
        jsonObject.addProperty("lastName", lastName);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);

        String json = new Gson().toJson(jsonObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        AuthService apiService = ApiClient.getClient().create(AuthService.class);

        Call<UserModel> call = apiService.setRegister(requestBody);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DisableLoading();
                    UserModel user = response.body();

                    if (authException != null) {
                        authException.onSuccess(user);
                    }
                } else {
                    DisableLoading();
                    if (authException != null) {
                        authException.onError("Registration failed." + response.message() + " Code: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                DisableLoading();
                if (authException != null) {
                    authException.onError("Registration failed. " + t.getMessage());
                }
            }
        });
    }

    //
    public void checkTokenValidity(String token, TokenValidity tokenValidity) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", token);

        String json = new Gson().toJson(jsonObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        AuthService apiService = ApiClient.getClient().create(AuthService.class);
        Call<JsonObject> call = apiService.checkTokenValidity(requestBody);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();

                    if (tokenValidity != null) {
                        tokenValidity.onSuccess(responseBody);
                    }
                } else {
                    tokenValidity.onError("Something went wrong." + response.message() + " Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                tokenValidity.onError("Failed to check token validity: " + t.getMessage());
            }
        });
    }

    ///
    //
    public void updateUserWithIdStudent(Student student, String email, AuthException authException) {
        JsonObject obj = new JsonObject();
        obj.addProperty("email", email);
        obj.addProperty("id_student", student.getIdStudent());

        String jsonString = obj.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

        AuthService apiService = ApiClient.getClientWithBearerToken(authToken).create(AuthService.class);
        Call<UserModel> call = apiService.updateUserWithIdStudent(requestBody);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    Log.d("AuthController", "Successfully");
                    authException.onSuccess(response.body());
                } else {
                    Log.d("AuthController", "Failed updateUserWithIDStudent: " + response.message() + " " + response.code() + response.body());

                    authException.onError("Error: " + response.message() + response.code() + response.body());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                authException.onError("Error: " + t.getMessage());
            }
        });
    }
}
