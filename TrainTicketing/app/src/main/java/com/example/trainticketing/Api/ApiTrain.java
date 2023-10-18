package com.example.trainticketing.Api;

import com.example.trainticketing.Service.TrainService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiTrain {
    public static Retrofit getRetrofit(){

//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();
                                // Request customization: add request headers
                                Request.Builder requestBuilder =original.newBuilder().
                                        method(original.method(), original.body());
                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        })
                .addInterceptor(interceptor).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();

//Assign the base url

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://trainbackend-d1ac1d62e1bd.herokuapp.com/api/TrainShedule/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }


    public static TrainService getTrainService(){
        TrainService trainService = getRetrofit().create(TrainService.class);
        return trainService;
    }
}
