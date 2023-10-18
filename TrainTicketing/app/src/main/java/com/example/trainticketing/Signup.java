package com.example.trainticketing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainticketing.Api.ApiBooking;
import com.example.trainticketing.Api.ApiUser;
import com.example.trainticketing.Model.Account;
import com.example.trainticketing.Model.BookingModel;
import com.example.trainticketing.Model.UserModel;
import com.example.trainticketing.Service.BookingService;
import com.example.trainticketing.Service.UserServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    EditText fullname, nic, phone, email, password, conpassword;
    Button signup;
    TextView signin;
//    UserDB db;
    AlertDialog.Builder builder2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname = findViewById(R.id.fullnametxt);
        nic = findViewById(R.id.nictxt);
        phone = findViewById(R.id.phonetxt);
        email = findViewById(R.id.emailtxt);
        password = findViewById(R.id.passwordtxt);
        conpassword = findViewById(R.id.conpasswordtxt);
        signup = findViewById(R.id.registorbtn);
        signin = findViewById(R.id.signintxt);

        builder2 = new AlertDialog.Builder(this);
//        db = new UserDB(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder2.setMessage("Do You Want To Book Now?")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                UserModel userModel = new UserModel();
                                userModel.setFullname(fullname.getText().toString());
                                userModel.setNic(nic.getText().toString());
                                userModel.setPhone(phone.getText().toString());
                                userModel.setEmail(email.getText().toString());
                                userModel.setPassword(password.getText().toString());

                                UserServices apiService = ApiUser.getRetrofit().create(UserServices.class);
                                Call call = apiService.addUser(userModel);
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onResponse(Call call, Response response) {
                                        if (response.isSuccessful()) {
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(), Success.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            Log.d("TAG","Response = "+response.code());

                                            Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Registration Cancelled",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder2.create();
                //Setting the title manually
                alert.setTitle("ATTENTION !!");
                alert.show();
            }
        });

//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String status = "true";
//                UserDB accountDB = new UserDB(getApplicationContext());
//                Account account = new Account();
//                account.setStatus(status);
//                account.setPassword(password.getText().toString());
//                account.setEmail(email.getText().toString());
//                account.setPhone(phone.getText().toString());
//                account.setNic(nic.getText().toString());
//                account.setFullname(fullname.getText().toString());
//                Account temp = accountDB.checkUsername(email.getText().toString());
////                if(fullname.equals("") || nic.equals("")|| phone.equals("") || email.equals("") || password.equals("") || conpassword.equals(""))
////                    Toast.makeText(Signup.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
////                    else{
////                    if(password.equals(conpassword)) {
//                        if (temp == null) {
//                            if (accountDB.create(account)) {
//                                Intent i = new Intent(Signup.this, MainActivity.class);
//                                startActivity(i);
//                                Toast.makeText(Signup.this, "Successfully Registered.", Toast.LENGTH_SHORT).show();
//
//
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                                builder.setTitle("Attention!!");
//                                builder.setMessage("Do You want to Cancel");
//                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.cancel();
//                                    }
//                                });
//                                builder.show();
//                            }
//                        } else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                            builder.setTitle("Error!!");
//                            builder.setMessage("Username Already Exist");
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.cancel();
//                                }
//                            });
//                            builder.show();
//
////                        }
////                    }
//                }
//            }
//        });

//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String fulln = fullname.getText().toString();
//                String nicc = nic.getText().toString();
//                String phonenon = phone.getText().toString();
//                String emaill = email.getText().toString();
//                String pass = password.getText().toString();
//                String conpass = conpassword.getText().toString();
//                String status = "true";
//
//                if(fulln.equals("") || nicc.equals("")|| phonenon.equals("") || emaill.equals("") || pass.equals("") || conpass.equals(""))
//                    Toast.makeText(Signup.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
//                else{
//                    if(pass.equals(conpass)){
//                        Boolean checkemaill = db.checkemail(emaill);
//                        if(checkemaill == false){
//                            Boolean insert = db.create(fulln, nicc,phonenon, emaill, pass, status);
//                            if(insert == true){
//                                Toast.makeText(Signup.this, "Registered Success", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(getApplicationContext(), Success.class);
//                                startActivity(i);
//                            }else{
//                                Toast.makeText(Signup.this, "Registered Failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        else{
//                            Toast.makeText(Signup.this, "Username already exists!", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(Signup.this, "Password not matching", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}