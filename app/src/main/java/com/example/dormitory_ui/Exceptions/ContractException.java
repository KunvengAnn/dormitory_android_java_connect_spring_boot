package com.example.dormitory_ui.Exceptions;

import com.example.dormitory_ui.models.Contract;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Objects;

public interface ContractException {
    void Success(List<Contract> contractLs);
    void onSuccess(Contract contract);
    void onSuccessTwo(JsonObject jsonObject);
    void Error(String msg);
}
