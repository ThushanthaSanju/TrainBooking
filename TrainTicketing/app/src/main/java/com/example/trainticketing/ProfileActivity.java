package com.example.trainticketing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.trainticketing.Adapter.TrainAdapter;
import com.example.trainticketing.Adapter.UserAdapter;
import com.example.trainticketing.Api.ApiBooking;
import com.example.trainticketing.Api.ApiUser;
import com.example.trainticketing.Model.Account;
import com.example.trainticketing.Model.BookingModel;
import com.example.trainticketing.Model.TrainModel;
import com.example.trainticketing.Model.UserModel;
import com.example.trainticketing.Service.BookingService;
import com.example.trainticketing.Service.UserServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, create, mybooking, history, profile;
    List<UserModel> list;
    Button logout,update, deactivate;

    EditText uname, nicc, phon, passw;
    UserAdapter userAdapter;
    RecyclerView recyclerView;

    AlertDialog.Builder builder2;

    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.homenav);
        create = findViewById(R.id.createBooknav);
        mybooking = findViewById(R.id.bookingnav);
        history = findViewById(R.id.historynav);
        profile = findViewById(R.id.profilenav);
        logout = findViewById(R.id.logoutbtnnav);
        builder2 = new AlertDialog.Builder(this);

        uname = findViewById(R.id.Nameittxt);
        nicc = findViewById(R.id.Nicittxt);
        phon = findViewById(R.id.Phoneedittxt);
        passw = findViewById(R.id.Passdittxt);
//        update = findViewById(R.id.updtbtnN);
//        deactivate = findViewById(R.id.cancelbtnN);

        recyclerView = findViewById(R.id.userpdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ProfileActivity.this, HomeActivity.class);

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ProfileActivity.this, CreateBookingActivity.class);
            }
        });

        mybooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ProfileActivity.this, MyBookingActivity.class);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ProfileActivity.this, HistoryActivity.class);
            }
        });

        profile.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        }));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String emaill = preferences.getString("emailKey", "");

//        UserDB accountDB = new UserDB(getApplicationContext());
//        Account currentAccountP = accountDB.find(emaill);
//        phon.setText(currentAccountP.getPhone());
//        nicc.setText(currentAccountP.getNic());
//        uname.setText(currentAccountP.getFullname());
//        passw.setText(currentAccountP.getPassword());

        list = new ArrayList<>();
        userAdapter = new UserAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(userAdapter);
        UserServices apiService = ApiUser.getRetrofit().create(UserServices.class);
        Call<List<UserModel>> call = apiService.getUserProfile(emaill);

        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                try {
                    if(response.isSuccessful()) {
//                        response.body();
//                        uname.setText(response.body().getFullname());
//                        nicc.setText(response.body().getNic());
//                        phon.setText(response.body().getPhone());
//                        passw.setText(response.body().getPassword());
                        list.addAll(response.body());
                        Log.d("TAG","Response = "+list);
                        userAdapter.setData(list);
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "errr", Toast.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

            }
        });

//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btnUpdate_onClick(view);
//            }
//        });

//        deactivate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btnDeactivate_onClick(view);
//            }
//        });
    }

    private void btnUpdate_onClick(View view) {

        builder2.setMessage("Do You Want To Update Your Booking?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserModel userModel = new UserModel();
                        userModel.setFullname(uname.getText().toString());
                        userModel.setNic(nicc.getText().toString());
                        userModel.setPhone(phon.getText().toString());
                        userModel.setPassword(passw.getText().toString());
                        String idd = userModel.get_id();

                        UserServices userServices = ApiBooking.getRetrofit().create(UserServices.class);

                        Call call = userServices.updateProfile(idd, userModel);

                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                if (response.isSuccessful()) {
                                    finish();
                                    Log.d("TAG","Response = "+response.code());
                                    Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Update Fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"Update Cancelled",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder2.create();
        //Setting the title manually
        alert.setTitle("ATTENTION !!");
        alert.show();
    }

    private void btnDeactivate_onClick(View view){
        try{
        builder2.setMessage("Are Sure To Deactivate Profile?")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String emaillq = preferences.getString("emailKey", "");
                        String status = "false";

                        UserDB accountDB = new UserDB(getApplicationContext());
                        Account currentAccount = accountDB.find(emaillq);
                        String newUsername = uname.getText().toString();
                        Account temp = accountDB.checkUsername(newUsername);

                        currentAccount.setStatus(status);
                        if(accountDB.update(currentAccount)){
                            Toast.makeText(ProfileActivity.this, "Profile Deactivated.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            builder.setTitle("ERROR !!");
                            builder.setMessage("Deactivation Failed");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder.show();

                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
//                            Toast.makeText(getApplicationContext(),"Update Cancelled",
//                                    Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder2.create();
        //Setting the title manually
        alert.setTitle("ATTENTION !!");
        alert.show();

    }catch (Exception e){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("ERROR !!");
        builder.setMessage("Email Already Exists");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
    }

//    private void btnUpdate_onClick(View view) {
//        try {
//            builder2.setMessage("Do You Want To Update Now?")
//                    .setCancelable(false)
//                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            String emaillq = preferences.getString("emailKey", "");
//
//            UserDB accountDB = new UserDB(getApplicationContext());
//            Account currentAccount = accountDB.find(emaillq);
//            String newUsername = uname.getText().toString();
//            Account temp = accountDB.checkUsername(newUsername);
//
//
//            currentAccount.setFullname(uname.getText().toString());
//            currentAccount.setNic(nicc.getText().toString());
//            currentAccount.setPhone(phon.getText().toString());
//            currentAccount.setPassword(passw.getText().toString());
//            if(accountDB.update(currentAccount)){
//                finish();
//                startActivity(getIntent());
//                Toast.makeText(ProfileActivity.this, "Successfully Updated.", Toast.LENGTH_SHORT).show();
//
//            }else {
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setTitle("ERROR !!");
//                builder.setMessage("Update Failed");
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//                builder.show();
//
//            }
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            //  Action for 'NO' Button
//                            dialog.cancel();
////                            Toast.makeText(getApplicationContext(),"Update Cancelled",
////                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//            //Creating dialog box
//            AlertDialog alert = builder2.create();
//            //Setting the title manually
//            alert.setTitle("ATTENTION !!");
//            alert.show();
//
//        }catch (Exception e){
//            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//            builder.setTitle("ERROR !!");
//            builder.setMessage("Email Already Exists");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.cancel();
//                }
//            });
//            builder.show();
//
//        }
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