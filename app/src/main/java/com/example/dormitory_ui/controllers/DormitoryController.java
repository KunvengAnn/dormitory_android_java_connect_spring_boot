package com.example.dormitory_ui.controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.dormitory_ui.Exceptions.DormitoryException;
import com.example.dormitory_ui.helper.SharePref;
import com.example.dormitory_ui.models.Dormitory;
import com.example.dormitory_ui.services.ApiClient;
import com.example.dormitory_ui.services.DormitoryService;
import com.example.dormitory_ui.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DormitoryController {
    private final Context context;
    private final String authToken;

    public DormitoryController(Context context) {
        this.context = context;
        this.authToken = SharePref.getInstance(context).getString(Constants.token, "");
    }

    public void getAllDormitoryData(DormitoryException dormitoryException) {
        DormitoryService dormitoryService = ApiClient.getClientWithBearerToken(authToken).create(DormitoryService.class);
        Call<List<Dormitory>> call = dormitoryService.getAllDormitory();
        call.enqueue(new Callback<List<Dormitory>>() {

            @Override
            public void onResponse(@NonNull Call<List<Dormitory>> call, @NonNull Response<List<Dormitory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Dormitory> dormitories = response.body();

                    dormitoryException.success(dormitories);
                } else {
                    dormitoryException.error("Error Dormitory: " + response.message() + response.code());

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Dormitory>> call, @NonNull Throwable t) {
                dormitoryException.error("Error Dormitory: " + t.getMessage());
            }
        });
    }
}
