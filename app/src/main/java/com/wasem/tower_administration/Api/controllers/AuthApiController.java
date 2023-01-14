package com.wasem.tower_administration.Api.controllers;


import com.wasem.tower_administration.Models.Admin;
import com.wasem.tower_administration.Models.BaseResponse;
import com.wasem.tower_administration.interfaces.ProcessCallback;
import com.wasem.tower_administration.prefs.AppSharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthApiController {

    public void login(Admin admin, ProcessCallback callback) {
        if (!admin.email.isEmpty() && !admin.password.isEmpty()) {
            admin.login(callback);
        }else {
            callback.onFailure("Enter required data!");
        }
    }

    public void logout(ProcessCallback callback) {
        Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequests().logout();
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200 || response.code() == 401) {
                    AppSharedPreferences.getInstance().clear();
                    callback.onSuccess(response.code() == 200 ? response.body().message : "Logged out successfully");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure("Something went wrong");
            }
        });
    }
}
