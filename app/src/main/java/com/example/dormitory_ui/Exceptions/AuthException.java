package com.example.dormitory_ui.Exceptions;

import com.example.dormitory_ui.models.UserModel;

public interface AuthException {
    void onSuccess(UserModel user);
    void onError(String errorMessage);
}
