package com.example.dormitory_ui.services;

import com.example.dormitory_ui.models.Room1;
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

public interface RoomService {
    @GET("rooms/getAllRooms")
    Call<List<Room1>> getAllRooms();

    @GET("rooms/{id}")
    Call<Room1> getRoomById(@Path("id") Integer id);

    @POST("rooms")
    Call<Room1> addRoom(@Body RequestBody requestBody);

    @PUT("rooms/{id}")
    Call<Room1> updateRoom(@Path("id") Integer id, @Body RequestBody requestBody);

    @DELETE("rooms/{id}")
    Call<JsonObject> deleteRoomById(@Path("id") Integer id);
}
