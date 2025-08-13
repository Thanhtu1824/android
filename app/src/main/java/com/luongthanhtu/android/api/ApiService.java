package com.luongthanhtu.android.api;

import com.luongthanhtu.android.model.User;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users") // endpoint trên MockAPI
    Call<List<User>> getUsers();
    @POST("users")
    Call<User> createUser(@Body User user);
    // Cập nhật thông tin user
    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") String id, @Body User user);

}
