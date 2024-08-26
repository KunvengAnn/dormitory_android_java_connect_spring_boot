package com.example.dormitory_ui.controllers;

import android.content.Context;
import android.util.Log;

import com.example.dormitory_ui.Exceptions.RoomException;
import com.example.dormitory_ui.helper.SharePref;
import com.example.dormitory_ui.models.Room;
import com.example.dormitory_ui.models.Room1;
import com.example.dormitory_ui.services.ApiClient;
import com.example.dormitory_ui.services.RoomService;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomController {
    private final Context contextRoom;
    private final String authToken;

    public RoomController(Context context) {
        this.contextRoom = context;
        this.authToken = SharePref.getInstance(context).getString(Constants.token, "");
    }

    public void getAllRoomData(RoomException room1Exception) {
        RoomService roomService = ApiClient.getClientWithBearerToken(authToken).create(RoomService.class);

        Call<List<Room1>> call = roomService.getAllRooms();
        call.enqueue(new Callback<List<Room1>>() {
            @Override
            public void onResponse(Call<List<Room1>> call, Response<List<Room1>> response) {
                if (response.isSuccessful()) {

                    List<Room1> room1Ls = response.body();
                    if (room1Exception != null) {
                        room1Exception.Success(room1Ls);
                    }
                } else {
                    if (room1Exception != null) {
                        room1Exception.Error("Error Room: " + response.message() + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Room1>> call, Throwable t) {
                if (room1Exception != null) {
                    room1Exception.Error("Error Room: " + t.getMessage());
                }
            }
        });
    }

    public void getRoomById(Integer id, RoomException room1Exception) {
        RoomService roomService = ApiClient.getClientWithBearerToken(authToken).create(RoomService.class);
        Call<Room1> call = roomService.getRoomById(id);

        call.enqueue(new Callback<Room1>() {
            @Override
            public void onResponse(Call<Room1> call, Response<Room1> response) {
                if (response.isSuccessful()) {

                    room1Exception.onSuccess(response.body());
                } else {
                    room1Exception.Error("Error Room: " + response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<Room1> call, Throwable t) {

                room1Exception.Error("Error Room: " + t.getMessage());
            }
        });
    }

    //
    public void addRoom(Room room, RoomException roomException) {
        RoomService roomService = ApiClient.getClientWithBearerToken(authToken).create(RoomService.class);
        String roomJson = new Gson().toJson(room);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), roomJson);

        Call<Room1> call = roomService.addRoom(requestBody);
        call.enqueue(new Callback<Room1>() {
            @Override
            public void onResponse(Call<Room1> call, Response<Room1> response) {
                if (response.isSuccessful()) {
                    Room1 room1 = response.body();
                    Log.d("RoomController", "room added successfully");
                    if (roomException != null) {
                        roomException.onSuccess(room1);
                    }
                } else {
                    Log.d("RoomController", "Error Room: " + response.message() + response.code());
                    if (roomException != null) {
                        roomException.Error("Error Room: " + response.message() + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Room1> call, Throwable t) {
                Log.d("RoomController", "Error fail room: " + t.getMessage());
                if (roomException != null) {
                    roomException.Error("Error Room: " + t.getMessage());
                }
            }
        });
    }

    //
    public void updateRoom(Integer id, Room room, RoomException roomException) {
        RoomService roomService = ApiClient.getClientWithBearerToken(authToken).create(RoomService.class);
        String room1Json = new Gson().toJson(room);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), room1Json);

        Call<Room1> call = roomService.updateRoom(id, requestBody);
        call.enqueue(new Callback<Room1>() {
            @Override
            public void onResponse(Call<Room1> call, Response<Room1> response) {
                if (response.isSuccessful()) {
                    Room1 room1 = response.body();
                    Log.d("RoomController", "success add Room");

                    roomException.onSuccess(room1);
                } else {
                    Log.d("RoomController", "error :" + response.code() + response.message() + response.body());

                    roomException.Error("Error Room: " + response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<Room1> call, Throwable t) {
                Log.d("RoomController", "Error fail:" + t.getMessage());

                roomException.Error("Error Room: " + t.getMessage());
            }
        });
    }

    //
    public void deleteRoomById(Integer id, RoomException roomException) {
        RoomService roomService = ApiClient.getClientWithBearerToken(authToken).create(RoomService.class);
        Call<JsonObject> call = roomService.deleteRoomById(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Loading", "onResponse: close roomController");
                    if (roomException != null) {
                        roomException.onSuccessTwo(response.body());
                    }
                } else {
                    roomException.Error("Error Room: " + response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                roomException.Error("Error Room: " + t.getMessage());
            }
        });
    }
}
