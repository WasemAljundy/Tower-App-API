package com.wasem.tower_administration.Api.controllers;


import com.wasem.tower_administration.Models.Advertisement;
import com.wasem.tower_administration.Models.BaseResponse;
import com.wasem.tower_administration.Models.Category;
import com.wasem.tower_administration.Models.CategoryShow;
import com.wasem.tower_administration.Models.Employee;
import com.wasem.tower_administration.Models.Operation;
import com.wasem.tower_administration.Models.User;
import com.wasem.tower_administration.interfaces.ListCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentApiController {

    private ApiController apiController;

    public ContentApiController() {
        apiController = ApiController.getInstance();
    }

    public void getUsers(ListCallback<User> callback) {
        Call<BaseResponse<User>> call = apiController.getRetrofitRequests().getUsers();
        call.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().list);
                } else {
                    callback.onFailure("No data");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {}
        });
    }

    public void getEmployees(ListCallback<Employee> callback) {
        Call<BaseResponse<Employee>> call = apiController.getRetrofitRequests().getEmployees();
        call.enqueue(new Callback<BaseResponse<Employee>>() {
            @Override
            public void onResponse(Call<BaseResponse<Employee>> call, Response<BaseResponse<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().list);
                } else {
                    callback.onFailure("No data");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Employee>> call, Throwable t) {}
        });
    }

    public void getCategories(ListCallback<Category> callback) {
        Call<BaseResponse<Category>> call = apiController.getRetrofitRequests().getCategories();
        call.enqueue(new Callback<BaseResponse<Category>>() {
            @Override
            public void onResponse(Call<BaseResponse<Category>> call, Response<BaseResponse<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().list);
                } else {
                    callback.onFailure("No data");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Category>> call, Throwable t) {}
        });
    }

    public void showCategories(int categoryId, ListCallback<CategoryShow> callback) {
        Call<BaseResponse<CategoryShow>> call = ApiController.getInstance().getRetrofitRequests().showCategories(categoryId);
        call.enqueue(new Callback<BaseResponse<CategoryShow>>() {
            @Override
            public void onResponse(Call<BaseResponse<CategoryShow>> call, Response<BaseResponse<CategoryShow>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().list);
                } else {
                    callback.onFailure("No data");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<CategoryShow>> call, Throwable t) {}
        });
    }

    public void getOperations(ListCallback<Operation> callback) {
        Call<BaseResponse<Operation>> call = ApiController.getInstance().getRetrofitRequests().getOperations();
        call.enqueue(new Callback<BaseResponse<Operation>>() {
            @Override
            public void onResponse(Call<BaseResponse<Operation>> call, Response<BaseResponse<Operation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().list);
                } else {
                    callback.onFailure("No data");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Operation>> call, Throwable t) {}
        });
    }

    public void getAdvertisements(ListCallback<Advertisement> callback) {
        Call<BaseResponse<Advertisement>> call = ApiController.getInstance().getRetrofitRequests().getAdvertisement();
        call.enqueue(new Callback<BaseResponse<Advertisement>>() {
            @Override
            public void onResponse(Call<BaseResponse<Advertisement>> call, Response<BaseResponse<Advertisement>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().list);
                } else {
                    callback.onFailure("No data");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Advertisement>> call, Throwable t) {}
        });
    }


}
