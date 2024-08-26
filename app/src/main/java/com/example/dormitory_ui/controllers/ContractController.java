package com.example.dormitory_ui.controllers;

import android.content.Context;
import android.util.Log;

import com.example.dormitory_ui.Exceptions.ContractException;
import com.example.dormitory_ui.helper.SharePref;
import com.example.dormitory_ui.models.Contract;
import com.example.dormitory_ui.services.ApiClient;
import com.example.dormitory_ui.services.ContractService;
import com.example.dormitory_ui.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractController {
    private final Context context;
    private final String authToken;

    public ContractController(Context context) {
        this.context = context;
        this.authToken = SharePref.getInstance(context).getString(Constants.token, "");
    }

    public void getAllContract(ContractException contractException) {
        ContractService contractService = ApiClient.getClientWithBearerToken(authToken).create(ContractService.class);
        Call<List<Contract>> call = contractService.getAllContract();

        call.enqueue(new Callback<List<Contract>>() {
            @Override
            public void onResponse(Call<List<Contract>> call, Response<List<Contract>> response) {
                if (response.isSuccessful()) {
                    contractException.Success(response.body());

                } else {
                    contractException.Error(response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Contract>> call, Throwable t) {
                contractException.Error(t.getMessage());
            }
        });
    }

    //
    public void getContractById(Integer id, ContractException contractException) {
        ContractService contractService = ApiClient.getClientWithBearerToken(authToken).create(ContractService.class);

        Call<Contract> call = contractService.getSingleContract(id);
        call.enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                if (response.isSuccessful()) {
                    if (contractException != null) {
                        contractException.onSuccess(response.body());
                    }
                } else {
                    if (contractException != null) {
                        contractException.Error(response.message() + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                if (contractException != null) {
                    contractException.Error(t.getMessage());
                }
            }
        });
    }

    //
    public void addContract(Contract contract, ContractException contractException) {
        ContractService contractService = ApiClient.getClientWithBearerToken(authToken).create(ContractService.class);

        String contractJson = new Gson().toJson(contract);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), contractJson);
        Call<Contract> call = contractService.addContract(requestBody);
        call.enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                if (response.isSuccessful()) {
                    Log.d("Loading", "onResponse: close ContractController");
                    Log.d("ContractController", "success add contract");

                    if (contractException != null) {
                        contractException.onSuccess(response.body());
                    }
                } else {
                    Log.d("ContractController", "Error : " + response.code() + response.message());

                    if (contractException != null) {
                        contractException.Error(response.message() + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                Log.d("ContractController", "Error fail contract: " + t.getMessage());
                if (contractException != null) {
                    contractException.Error(t.getMessage());
                }
            }
        });
    }

    //
    public void updateContract(Integer id, Contract contract, ContractException contractException) {
        ContractService contractService = ApiClient.getClientWithBearerToken(authToken).create(ContractService.class);

        String contractJson = new Gson().toJson(contract);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), contractJson);

        Call<Contract> call = contractService.updateContract(id, requestBody);
        call.enqueue(new Callback<Contract>() {
            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {
                if (response.isSuccessful()) {
                    if (contractException != null) {
                        contractException.onSuccess(response.body());
                    }
                } else {
                    if (contractException != null) {
                        contractException.Error(response.message() + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                if (contractException != null) {
                    contractException.Error(t.getMessage());
                }
            }
        });
    }

    public void deleteContractById(Integer id, ContractException contractException) {
        ContractService contractService = ApiClient.getClientWithBearerToken(authToken).create(ContractService.class);
        Call<JsonObject> call = contractService.deleteSingleContract(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    contractException.onSuccessTwo(response.body());
                } else {
                    contractException.Error(response.message() + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                contractException.Error(t.getMessage());
            }
        });
    }
}
