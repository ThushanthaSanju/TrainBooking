package com.example.trainticketing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, create, mybooking, history, profile;

    TextView fname, femal;

    Button logout, book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.homenav);
        create = findViewById(R.id.createBooknav);
        mybooking = findViewById(R.id.bookingnav);
        history = findViewById(R.id.historynav);
        profile = findViewById(R.id.profilenav);
        logout = findViewById(R.id.logoutbtnnav);
        book = findViewById(R.id.bookkbtn);
        fname = findViewById(R.id.nametxtnav);
        femal = findViewById(R.id.emailtxtnav);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = preferences.getString("emailKey", "");
        String nme = preferences.getString("nameKey", "");

        femal.setText(email);
        fname.setText(nme);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateBookingActivity.class);
                startActivity(i);
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
                recreate();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this, CreateBookingActivity.class);
            }
        });

        mybooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this, MyBookingActivity.class);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this, HistoryActivity.class);
            }
        });

        profile.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomeActivity.this, ProfileActivity.class);
            }
        }));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
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