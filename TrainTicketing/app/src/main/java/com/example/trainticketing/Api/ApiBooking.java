package com.example.trainticketing.Api;

import com.example.trainticketing.Service.BookingService;
import com.example.trainticketing.Service.TrainService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBooking {
    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

//Assign the base url
        //http://10.0.2.2:9292/api/TrainBooking
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://trainbackend-d1ac1d62e1bd.herokuapp.com/api/TrainBooking/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }


    public static BookingService getBookingService(){
        BookingService bookingService = getRetrofit().create(BookingService.class);
        return bookingService;
    }
}
