package com.example.cowinvaccinenotifier.network;


import com.example.cowinvaccinenotifier.network.properties.SessionClass;
import com.example.cowinvaccinenotifier.network.properties.Sessions;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CowinApiService {

    @Headers("User-Agent: Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.210 Mobile Safari/537.36")
    @GET("v2/appointment/sessions/public/findByPin")
    Call<SessionClass> getSessions(
        @Query("pincode") String pincode,
        @Query("date") String date
    );



}
