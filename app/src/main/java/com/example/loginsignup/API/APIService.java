package com.example.loginsignup.API;


import com.example.loginsignup.Model.Data;
import com.example.loginsignup.Model.User;
import com.example.loginsignup.Model.UserList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;


import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {

    @POST("login")
    Call<User> userLogIn(@Body User user);

    @POST("register")
    Call<User> userSignUp(@Body User user);

    @GET("/api/users")
    Call<UserList> getData(@Query("page") int pageNumber);

}
