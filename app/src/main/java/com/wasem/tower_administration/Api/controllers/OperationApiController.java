package com.wasem.tower_administration.Api.controllers;

import com.wasem.tower_administration.Models.BaseResponse;
import com.wasem.tower_administration.Models.Operation;
import com.wasem.tower_administration.interfaces.ProcessCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationApiController {

    public void addOperation(long categoryId, int amount, String details, long actorId, String actorType, String date, ProcessCallback callback) {
        Call<BaseResponse<Operation>> call = ApiController.getInstance().getRetrofitRequests().storeOperation(categoryId,amount,details,actorId,actorType,date);
        call.enqueue(new Callback<BaseResponse<Operation>>() {
            @Override
            public void onResponse(Call<BaseResponse<Operation>> call, Response<BaseResponse<Operation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().message);
                } else {
                    try {
                        String error = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(error);
                        callback.onFailure(jsonObject.getString("message"));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Operation>> call, Throwable t) {
                callback.onFailure("Something went wrong");
            }
        });
    }

    public void updateOperation(int id, long categoryId, int amount, String details, long actorId, String actorType, String date, ProcessCallback callback) {
        Call<BaseResponse<Operation>> call = ApiController.getInstance().getRetrofitRequests().updateOperation(id, categoryId, amount, details, actorId, actorType, date);
        call.enqueue(new Callback<BaseResponse<Operation>>() {
            @Override
            public void onResponse(Call<BaseResponse<Operation>> call, Response<BaseResponse<Operation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().message);
                } else {
                    try {
                        String error = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(error);
                        callback.onFailure(jsonObject.getString("message"));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Operation>> call, Throwable t) {
                callback.onFailure("Something went wrong");
            }
        });
    }

    public void deleteOperation(int id, ProcessCallback callback) {
        Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequests().deleteOperation(id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Operation deleted successfully!");
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure("Can't delete this Operation!");
            }
        });
    }
}
