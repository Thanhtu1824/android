package com.luongthanhtu.android.api;

import com.luongthanhtu.android.model.User;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("users") // endpoint trên MockAPI
    Call<List<User>> getUsers();
    @POST("users")
    Call<User> createUser(@Body User user);

}
