package com.example.jdawgsquareservices;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/v2/customers")
    Call<User> getUserInformation(@Field("phone") String phone, @Field("note") String note);

}
