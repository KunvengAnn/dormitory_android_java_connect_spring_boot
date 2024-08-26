package com.example.dormitory_ui.services;

import com.example.dormitory_ui.models.Contract;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContractService {
    @GET("contract")
    Call<List<Contract>> getAllContract();

    @GET("contract/{id}")
    Call<Contract> getSingleContract(@Path("id") Integer id);

    @POST("contract")
    Call<Contract> addContract(@Body RequestBody requestBody);

    @PUT("contract/{id}")
    Call<Contract> updateContract(@Path("id") Integer id,@Body RequestBody requestBody);

    @DELETE("contract/{id}")
    Call<JsonObject> deleteSingleContract(@Path("id") Integer id);
}
