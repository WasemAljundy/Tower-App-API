package com.wasem.tower_administration.Api;
import com.wasem.tower_administration.Models.Admin;
import com.wasem.tower_administration.Models.Advertisement;
import com.wasem.tower_administration.Models.BaseResponse;
import com.wasem.tower_administration.Models.Category;
import com.wasem.tower_administration.Models.CategoryShow;
import com.wasem.tower_administration.Models.Employee;
import com.wasem.tower_administration.Models.Operation;
import com.wasem.tower_administration.Models.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitRequests {

    @FormUrlEncoded
    @POST("auth/login")
    Call<BaseResponse<Admin>> login(@Field("email") String email, @Field("password") String password);

    @GET("auth/logout")
    Call<BaseResponse> logout();

    //***********************

    @GET("users")
    Call<BaseResponse<User>> getUsers();

    @Multipart
    @POST("users")
    Call<BaseResponse<User>> storeUser(@Part("name") RequestBody name, @Part("email") RequestBody email, @Part("mobile") RequestBody mobile, @Part("national_number") RequestBody national_number, @Part("family_members") RequestBody family_members, @Part("gender") RequestBody gender, @Part MultipartBody.Part image);

    @Multipart
    @POST("users/{id}")
    Call<BaseResponse<User>> updateUser(@Path("id") int id, @Part("_method") RequestBody method, @Part("name") RequestBody name, @Part("mobile") RequestBody mobile, @Part("national_number") RequestBody national_number, @Part("family_members") RequestBody family_members, @Part("gender") RequestBody gender, @Part MultipartBody.Part image);

    @DELETE("users/{id}")
    Call<BaseResponse> deleteUser(@Path("id") int id);

    //***********************

    @GET("employees")
    Call<BaseResponse<Employee>> getEmployees();

    @Multipart
    @POST("employees")
    Call<BaseResponse<Employee>> storeEmployee(@Part("name") String name, @Part("mobile") String mobile, @Part("national_number") String national_number, @Part MultipartBody.Part image);

    @Multipart
    @PUT("employees/{id}")
    Call<BaseResponse<Employee>> updateEmployee(@Path("id") int id, @Part("name") String name, @Part("mobile") String mobile, @Part("national_number") String national_number, @Part MultipartBody.Part image, @Part("_method") String method);

    @DELETE("employees/{id}")
    Call<BaseResponse> deleteEmployee(@Path("id") int id);

    //***********************

    @GET("categories")
    Call<BaseResponse<Category>> getCategories();

    @GET("categories/{id}")
    Call<BaseResponse<CategoryShow>> showCategories(@Path("id") int id);

    //***********************

    @GET("operations")
    Call<BaseResponse<Operation>> getOperations();

    @FormUrlEncoded
    @POST("operations")
    Call<BaseResponse<Operation>> storeOperation(@Field("category_id") long category_id, @Field("amount") int amount, @Field("details") String details, @Field("actor_id") long actor_id, @Field("actor_type") String actor_type, @Field("date") String date) ;

    @FormUrlEncoded
    @PUT("operations/{id}")
    Call<BaseResponse<Operation>> updateOperation(@Path("id") long id, @Field("category_id") long category_id, @Field("amount") int amount, @Field("details") String details, @Field("actor_id") long actor_id, @Field("actor_type") String actor_type, @Field("date") String date);

    @DELETE("operations/{id}")
    Call<BaseResponse> deleteOperation(@Path("id") int id);

    //***********************

    @GET("advertisements")
    Call<BaseResponse<Advertisement>> getAdvertisement();

    @Multipart
    @POST("advertisements")
    Call<BaseResponse<Advertisement>> storeAdvertisement(@Part("title") RequestBody title, @Part("info") RequestBody info, @Part MultipartBody.Part image);

    @Multipart
    @POST("advertisements/{id}")
    Call<BaseResponse<Advertisement>> updateAdvertisement(@Path("id") int id, @Part("_method") RequestBody _method, @Part("title") RequestBody title, @Part("info") RequestBody info, @Part MultipartBody.Part image);

    @DELETE("advertisements/{id}")
    Call<BaseResponse> deleteAdvertisement(@Path("id") int id);
}
