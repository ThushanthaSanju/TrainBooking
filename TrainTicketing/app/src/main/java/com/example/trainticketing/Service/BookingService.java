package com.example.trainticketing.Service;

import com.example.trainticketing.Model.BookingModel;
import com.example.trainticketing.Model.TrainModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BookingService {

    @GET("TrainBookingGetAll/")
    Call<List<BookingModel>> getAllBookings();

    //Get My bookings for the specific user
    @GET("TrainBookingGetByEmail/{email}")
    Call<List<BookingModel>> getMyBookings(@Path("email") String email);

//    @POST("bookTrain/")
//    Call<BookingModel> addBooking(@Body BookingModel bookingModel);
    @POST("TrainBookingInsert/")
    Call<Void> addBooking(@Body BookingModel bookingModel);

    //user update booking data
    @PUT("{id}")
    Call<Void> updateBooking(@Path("id") String _id, @Body BookingModel bookingModel);
}
