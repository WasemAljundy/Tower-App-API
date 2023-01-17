package com.wasem.tower_administration.Api.controllers;

import com.wasem.tower_administration.Models.BaseResponse;
import com.wasem.tower_administration.Models.Employee;
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

public class EmployeeApiController {

    public void addEmployee(RequestBody name, RequestBody mobile, RequestBody nationalNumber, MultipartBody.Part image, ProcessCallback callback) {
        Call<BaseResponse<Employee>> call = ApiController.getInstance().getRetrofitRequests().storeEmployee(name, mobile, nationalNumber, image);
        call.enqueue(new Callback<BaseResponse<Employee>>() {
            @Override
            public void onResponse(Call<BaseResponse<Employee>> call, Response<BaseResponse<Employee>> response) {
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
            public void onFailure(Call<BaseResponse<Employee>> call, Throwable t) {
                callback.onFailure("Something went wrong");
            }
        });
    }

    public void updateEmployee(int id, RequestBody name, RequestBody mobile, RequestBody nationalNumber, MultipartBody.Part image, RequestBody _method, ProcessCallback callback) {
        Call<BaseResponse<Employee>> call = ApiController.getInstance().getRetrofitRequests().updateEmployee(id, name, mobile, nationalNumber, image, _method);
        call.enqueue(new Callback<BaseResponse<Employee>>() {
            @Override
            public void onResponse(Call<BaseResponse<Employee>> call, Response<BaseResponse<Employee>> response) {
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
            public void onFailure(Call<BaseResponse<Employee>> call, Throwable t) {
                callback.onFailure("Something went wrong");
            }
        });
    }

    public void deleteEmployee(int id, ProcessCallback callback) {
        Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequests().deleteEmployee(id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Employee deleted successfully!");
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure("Can't delete this Employee!");
            }
        });
    }
}
