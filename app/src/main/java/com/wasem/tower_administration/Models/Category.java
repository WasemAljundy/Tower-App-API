package com.wasem.tower_administration.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("actions_count")
    @Expose
    public String actionsCount;
    @SerializedName("total")
    @Expose
    public int total;
}
