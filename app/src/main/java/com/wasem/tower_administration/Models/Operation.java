package com.wasem.tower_administration.Models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wasem.tower_administration.Api.controllers.ApiController;
import com.wasem.tower_administration.interfaces.ProcessCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Operation {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("category_id")
    @Expose
    public long categoryId;
    @SerializedName("amount")
    @Expose
    public int amount;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("actor_id")
    @Expose
    public long actorId;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("category_name")
    @Expose
    public String categoryName;
    @SerializedName("actor")
    @Expose
    public Object actor;
    private String actorType;

}
