package com.wasem.tower_administration.Models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wasem.tower_administration.Api.controllers.ApiController;
import com.wasem.tower_administration.interfaces.ProcessCallback;
import com.wasem.tower_administration.prefs.AppSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("national_number")
    @Expose
    public String nationalNumber;
    @SerializedName("family_members")
    @Expose
    public String familyMembers;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("tower_name")
    @Expose
    public String towerName;

//    public void addUser(ProcessCallback callback) {
//        Call<BaseResponse<User>> call = ApiController.getInstance().getRetrofitRequests().storeUser(name,email,mobile,nationalNumber,familyMembers,gender,imageUrl);
//        call.enqueue(new Callback<BaseResponse<User>>() {
//            @Override
//            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    callback.onSuccess(response.body().message);
//                } else {
//                    try {
//                        String error = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
//                        JSONObject jsonObject = new JSONObject(error);
//                        Log.e("Retrofit-API", "onResponse: " + error);
//                        Log.e("Retrofit-API", "onResponse: " + jsonObject.getString("message"));
//                        callback.onFailure(jsonObject.getString("message"));
//                    } catch (IOException | JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
//                callback.onFailure("Something went wrong");
//            }
//        });
//    }
}
