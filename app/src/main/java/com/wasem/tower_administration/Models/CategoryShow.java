package com.wasem.tower_administration.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wasem.tower_administration.Api.controllers.ApiController;
import com.wasem.tower_administration.interfaces.ListCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryShow {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("category_id")
    @Expose
    public String categoryId;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("actor_id")
    @Expose
    public String actorId;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("category_name")
    @Expose
    public String categoryName;

}
