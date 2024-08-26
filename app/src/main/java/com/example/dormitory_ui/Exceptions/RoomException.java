package com.example.dormitory_ui.Exceptions;

import com.example.dormitory_ui.models.Room1;
import com.google.gson.JsonObject;

import java.util.List;

public interface RoomException {
    void Success(List<Room1> room1Ls);
    void onSuccess(Room1 room);
    void onSuccessTwo(JsonObject jsonObject);
    void Error(String msg);
}
