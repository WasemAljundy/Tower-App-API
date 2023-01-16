package com.wasem.tower_administration.Api.controllers;

import com.wasem.tower_administration.Models.Advertisement;
import com.wasem.tower_administration.Models.BaseResponse;
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

public class AdvertisementApiController {

    public void addAdvertisement(RequestBody title, RequestBody info, MultipartBody.Part image, ProcessCallback callback) {
        Call<BaseResponse<Advertisement>> call = ApiController.getInstance().getRetrofitRequests().storeAdvertisement(title, info, image);
        call.enqueue(new Callback<BaseResponse<Advertisement>>() {
            @Override
            public void onResponse(Call<BaseResponse<Advertisement>> call, Response<BaseResponse<Advertisement>> response) {
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
            public void onFailure(Call<BaseResponse<Advertisement>> call, Throwable t) {
                callback.onFailure("Something went wrong");
            }
        });
    }

    public void updateAdvertisement(int id, RequestBody _method, RequestBody title, RequestBody info, MultipartBody.Part image, ProcessCallback callback) {
        Call<BaseResponse<Advertisement>> call = ApiController.getInstance().getRetrofitRequests().updateAdvertisement(id, _method, title, info, image);
        call.enqueue(new Callback<BaseResponse<Advertisement>>() {
            @Override
            public void onResponse(Call<BaseResponse<Advertisement>> call, Response<BaseResponse<Advertisement>> response) {
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
            public void onFailure(Call<BaseResponse<Advertisement>> call, Throwable t) {
                callback.onFailure("Something went wrong");
            }
        });
    }

    public void deleteAdvertisement(int id, ProcessCallback callback) {
        Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequests().deleteAdvertisement(id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Advertisement deleted successfully!");
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure("Can't delete this Advertisement!");
            }
        });
    }
}
