package com.shahinsha.shootmovements.retrofit;

import com.shahinsha.shootmovements.model.fcm.RootModel;
import com.shahinsha.shootmovements.utility.Constants;
import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({"Authorization: key=" + Constants.SERVER_KEY, "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body RootModel root);
}