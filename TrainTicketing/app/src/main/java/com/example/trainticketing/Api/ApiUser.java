package com.example.trainticketing.Api;

import com.example.trainticketing.Service.BookingService;
import com.example.trainticketing.Service.UserServices;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUser {

    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

//Assign the base url

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://trainbackend-d1ac1d62e1bd.herokuapp.com/api/User/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }


    public static UserServices getUserService(){
        UserServices userServices = getRetrofit().create(UserServices.class);
        return userServices;
    }
}
