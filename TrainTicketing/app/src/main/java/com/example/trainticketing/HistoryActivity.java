package com.example.trainticketing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainticketing.Adapter.BookingAdapter;
import com.example.trainticketing.Adapter.HistoryAdapter;
import com.example.trainticketing.Api.ApiBooking;
import com.example.trainticketing.Api.ApiTrain;
import com.example.trainticketing.Model.BookingModel;
import com.example.trainticketing.Model.TrainModel;
import com.example.trainticketing.Service.BookingService;
import com.example.trainticketing.Service.TrainService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, create, mybooking, history, profile;

    RecyclerView recyclerView;
    List<BookingModel> list;
    HistoryAdapter historyAdapter;
    Button logout;
    TextView fname, femal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.homenav);
        create = findViewById(R.id.createBooknav);
        mybooking = findViewById(R.id.bookingnav);
        history = findViewById(R.id.historynav);
        profile = findViewById(R.id.profilenav);
        logout = findViewById(R.id.logoutbtnnav);

        fname = findViewById(R.id.nametxtnav);
        femal = findViewById(R.id.emailtxtnav);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = preferences.getString("emailKey", "");
        String nme = preferences.getString("nameKey", "");

        femal.setText(email);
        fname.setText(nme);

        //adapter

        recyclerView = findViewById(R.id.historydapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        historyAdapter = new HistoryAdapter(getApplicationContext(),list);

        recyclerView.setAdapter(historyAdapter);
        BookingService apiService = ApiBooking.getRetrofit().create(BookingService.class);
        Call<List<BookingModel>> call = apiService.getAllBookings();

        //get all bookings
        call.enqueue(new Callback<List<BookingModel>>() {
            @Override
            public void onResponse(Call<List<BookingModel>> call, Response<List<BookingModel>> response) {
                list.addAll(response.body());
                Log.d("TAG","Response = "+list);
                historyAdapter.setData(list);
                Toast.makeText(HistoryActivity.this,"success data", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<BookingModel>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
                Toast.makeText(HistoryActivity.this,"Data Retrieve Failed", Toast.LENGTH_LONG).show();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HistoryActivity.this, HomeActivity.class);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HistoryActivity.this, CreateBookingActivity.class);
            }
        });

        mybooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HistoryActivity.this, MyBookingActivity.class);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        profile.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HistoryActivity.this, ProfileActivity.class);
            }
        }));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HistoryActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent i = new Intent(activity, secondActivity);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
        activity.finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
}