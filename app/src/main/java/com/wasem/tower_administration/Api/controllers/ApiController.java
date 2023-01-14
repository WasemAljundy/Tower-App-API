package com.wasem.tower_administration.Api.controllers;
import com.wasem.tower_administration.Api.RetrofitRequests;
import com.wasem.tower_administration.Api.RetrofitSettings;

public class ApiController {

    private RetrofitRequests retrofitRequests;
    private static ApiController instance;

    private ApiController() {
        retrofitRequests = RetrofitSettings.getInstance().create(RetrofitRequests.class);
    }

    public static synchronized ApiController getInstance() {
        if(instance == null) {
            instance = new ApiController();
        }
        return instance;
    }

    public RetrofitRequests getRetrofitRequests() {
        return retrofitRequests;
    }
}
