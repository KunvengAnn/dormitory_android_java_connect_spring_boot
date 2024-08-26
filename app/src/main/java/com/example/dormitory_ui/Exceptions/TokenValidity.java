package com.example.dormitory_ui.Exceptions;

import com.google.gson.JsonObject;

public interface TokenValidity {
    void onSuccess(JsonObject response);
    void onError(String errorMessage);
}
