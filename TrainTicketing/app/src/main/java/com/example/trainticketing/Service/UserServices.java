package com.example.trainticketing.Service;

import com.example.trainticketing.Model.BookingModel;
import com.example.trainticketing.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserServices {

    @GET("UserGetAll/")
    Call<List<UserModel>> getUsers();

    //Get My bookings for the specific user
//    @GET("TrainBookingGetByEmail/{email}")
//    Call<List<UserModel>> getUser(@Path("email") String email);

    //Owner Login
    @POST("signin/")
    Call<UserModel> userLogin(@Body UserModel userModel);

    @POST("userInsert/")
    Call<Void> addUser(@Body UserModel userModel);

    @GET("UserByEmail/{email}")
    Call<List<UserModel>> getUserProfile(@Path("email") String email);

    @PUT("{id}")
    Call<Void> updateProfile(@Path("id") String _id, @Body UserModel userModel);
}
