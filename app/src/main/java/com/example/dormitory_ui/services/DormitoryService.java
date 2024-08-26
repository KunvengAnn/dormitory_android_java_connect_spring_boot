package com.example.dormitory_ui.services;

import com.example.dormitory_ui.models.Dormitory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DormitoryService {

    @GET("dormitory/getAllDormitory")
    Call<List<Dormitory>> getAllDormitory();
}
