package com.example.trainticketing.Service;

import com.example.trainticketing.Model.TrainModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TrainService {
    //Get All Fuel Station
    @GET("TrainSheduleGetAll/")
    Call<List<TrainModel>> getAllTrains();

//    //Get Fuel Station for the specific Owner
    @GET("search/{From}/{To}")
    Call<List<TrainModel>> getSearch(@Path("From") String From, @Path("To") String To);
//
//    //Add New Fuel Station when Owner Registering
//    @POST("insertFuelS/")
//    Call<TrainModel> addFuelStation(@Body TrainModel fuelModel);
//
//    //Owner Login
//    @POST("signin/")
//    Call<TrainModel> stationLogin(@Body TrainModel fuelModel);
//
//    //Owner update station data
//    @PUT("{id}")
//    Call<Void> updateFuelStation(@Path("id") String _id, @Body TrainModel fuelModel);
}
