package com.example.trainticketing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainticketing.Adapter.TrainAdapter;
import com.example.trainticketing.Api.ApiTrain;
import com.example.trainticketing.Model.TrainModel;
import com.example.trainticketing.Service.TrainService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBookingActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, create, mybooking, history, profile;

    EditText from, to;
    RecyclerView recyclerView;
    List<TrainModel> list;
    TrainAdapter trainAdapter;

    Button logout, search;

    TextView fname, femal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);

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

        fname.setText(nme);
        femal.setText(email);
//        search = findViewById(R.id.searchbtn);

        //adapter

        from = findViewById(R.id.fromedittxt);
//        to = findViewById(R.id.toeditTxt);
        recyclerView = findViewById(R.id.trainadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        trainAdapter = new TrainAdapter(getApplicationContext(),list);

        recyclerView.setAdapter(trainAdapter);
        TrainService apiService = ApiTrain.getRetrofit().create(TrainService.class);
        Call<List<TrainModel>> call = apiService.getAllTrains();

        //get all fuel Stations
        call.enqueue(new Callback<List<TrainModel>>() {
            @Override
            public void onResponse(Call<List<TrainModel>> call, Response<List<TrainModel>> response) {
                list.addAll(response.body());
                Log.d("TAG","Response = "+list);
                trainAdapter.setData(list);
                Toast.makeText(CreateBookingActivity.this,"success data", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<TrainModel>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
                Toast.makeText(CreateBookingActivity.this,"Data Retrieve Failed", Toast.LENGTH_LONG).show();
            }
        });

//        getDAta();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(CreateBookingActivity.this, HomeActivity.class);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        mybooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(CreateBookingActivity.this, MyBookingActivity.class);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(CreateBookingActivity.this, HistoryActivity.class);
            }
        });

        profile.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(CreateBookingActivity.this, ProfileActivity.class);
            }
        }));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateBookingActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (from.toString().equals("")) {
//                    search.setEnabled(false);
//                } else {
//                    if(!to.getText().equals("")) //reference line **1**
//                        search.setEnabled(true);
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                TrainService apiService = ApiTrain.getRetrofit().create(TrainService.class);
//                Call<List<TrainModel>> call = apiService.getSearch(from.getText().toString(), to.getText().toString());
//
//                //get all fuel Stations
//                call.enqueue(new Callback<List<TrainModel>>() {
//                    @Override
//                    public void onResponse(Call<List<TrainModel>> call, Response<List<TrainModel>> response) {
//                        list.addAll(response.body());
//                        Log.d("TAG","Response = "+list);
//                        trainAdapter.setData(list);
//                        recyclerView.setAdapter(trainAdapter);                    }
//
//                    @Override
//                    public void onFailure(Call<List<TrainModel>> call, Throwable t) {
//                        Log.d("TAG","Response = "+t.toString());
//                        Toast.makeText(CreateBookingActivity.this,"Data Retrieve Failed", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });



    }

    //Filter Search data
    private void filter(String text){
        ArrayList<TrainModel> filterList = new ArrayList<>();

        for (TrainModel item: list){
            if (item.getFrom().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        trainAdapter.setData(filterList);
    }

//    private void getDAta() {
//
//        list = new ArrayList<>();
//        trainAdapter = new TrainAdapter(getApplicationContext(),list);
//
//        recyclerView.setAdapter(trainAdapter);
//
//        String trainname = getIntent().getStringExtra("name");
//        String price = getIntent().getStringExtra("PriceForOneSeat");
//
//        TrainService apiService = ApiTrain.getRetrofit().create(TrainService.class);
//        Call<List<TrainModel>> call2 = apiService.getAllTrains();
//
//        call2.enqueue(new Callback<List<TrainModel>>() {
//            @Override
//            public void onResponse(Call<List<TrainModel>> call, Response<List<TrainModel>> response) {
//                list.addAll(response.body());
//                Log.d("TAG","Response = "+list);
//                trainAdapter.setData(list);
//                trainAdapter.notifyDataSetChanged();
//                Toast.makeText(CreateBookingActivity.this,"success data", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<TrainModel>> call, Throwable t) {
//                Toast.makeText(CreateBookingActivity.this,"Data Retrieve Failed", Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }

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