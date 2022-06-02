package com.shahinsha.shootmovements.retrofit;


import com.shahinsha.shootmovements.model.Login;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiRequest {


    //login /signup process
    @POST("api/authaccount/login")
    Call<Login> loginemail(@Body Map<String, String> body);

    @POST("api/authaccount/registration")
    Call<Login> signup(@Body Map<String, String> body);



}
