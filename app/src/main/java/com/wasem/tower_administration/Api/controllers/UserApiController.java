package com.wasem.tower_administration.Api.controllers;

import com.wasem.tower_administration.Models.BaseResponse;
import com.wasem.tower_administration.Models.Operation;
import com.wasem.tower_administration.Models.User;
import com.wasem.tower_administration.interfaces.ProcessCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserApiController {

    public void addUser(RequestBody name, RequestBody email, RequestBody mobile, RequestBody nationalNumber, RequestBody familyMembers, RequestBody gender, MultipartBody.Part image, ProcessCallback callback) {
        Call<BaseResponse<User>> call = ApiController.getInstance().getRetrofitRequests().storeUser(name,email,mobile,nationalNumber,familyMembers,gender,image);
        call.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
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
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                callback.onFailure("Something went wrong");
            }
        });
    }

    public void updateUser(int id, RequestBody _method, RequestBody name, RequestBody mobile, RequestBody nationalNumber, RequestBody familyMembers, RequestBody gender, MultipartBody.Part image, ProcessCallback callback) {
        Call<BaseResponse<User>> call = ApiController.getInstance().getRetrofitRequests().updateUser(id, _method, name, mobile, nationalNumber, familyMembers, gender, image);
        call.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
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
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                callback.onFailure("Something went wrong");
            }
        });
    }

    public void deleteUser(int id, ProcessCallback callback) {
        Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequests().deleteUser(id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("User deleted successfully!");
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure("Can't delete this User!");
            }
        });
    }
}
