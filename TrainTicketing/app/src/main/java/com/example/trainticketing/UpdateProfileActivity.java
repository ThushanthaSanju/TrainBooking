package com.example.trainticketing;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.example.trainticketing.Api.ApiBooking;
import com.example.trainticketing.Api.ApiUser;
import com.example.trainticketing.Model.BookingModel;
import com.example.trainticketing.Model.UserModel;
import com.example.trainticketing.Service.BookingService;
import com.example.trainticketing.Service.UserServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText name, nic,phone,password;
    Button update, deact, back;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        name = findViewById(R.id.Nameittxtu);
        nic = findViewById(R.id.Nicittxtu);
        phone = findViewById(R.id.Phoneedittxtu);
        password = findViewById(R.id.Passdittxtu);
        back =findViewById(R.id.button2);

        update = findViewById(R.id.updtbtnNu);
        deact = findViewById(R.id.cancelbtnNu);
        builder = new AlertDialog.Builder(this);
        String idd = getIntent().getStringExtra("id");
        String uname = getIntent().getStringExtra("name");
        String unic = getIntent().getStringExtra("nic");
        String phn = getIntent().getStringExtra("phone");
        String eml = getIntent().getStringExtra("email");
        String pass = getIntent().getStringExtra("password");

        name.setText(uname);
        nic.setText(unic);
        phone.setText(phn);
        password.setText(pass);


//        String email = PreferenceManager.getDefaultSharedPreferences(this).getString("EMAIL", "");


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = preferences.getString("emailKey", "");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Do You Want To Update Your Booking?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                UserModel userModel = new UserModel();
                                userModel.setFullname(name.getText().toString());
                                userModel.setNic(nic.getText().toString());
                                userModel.setPhone(phone.getText().toString());
                                userModel.setPassword(password.getText().toString());

                                UserServices userServices = ApiUser.getRetrofit().create(UserServices.class);

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
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("ATTENTION !!");
                alert.show();
            }
        });

        deact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = "false";
                builder.setMessage("Are Sure To Deactivate Profile?")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                UserModel userModel = new UserModel();
                                userModel.setStatus(status);

                                UserServices userServices = ApiUser.getRetrofit().create(UserServices.class);

                                Call call = userServices.updateProfile(idd, userModel);

                                call.enqueue(new Callback() {
                                    @Override
                                    public void onResponse(Call call, Response response) {
                                        if (response.isSuccessful()) {
                                            finish();
                                            Log.d("TAG","Response = "+response.code());
                                            Toast.makeText(getApplicationContext(), "Profile Deactivated.", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(i);
                                        }
                                        else{
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
                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Cancelled",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("ATTENTION !!");
                alert.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
            }
        });


    }
}