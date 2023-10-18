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
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainticketing.Api.ApiUser;
import com.example.trainticketing.Model.Account;
import com.example.trainticketing.Model.UserModel;
import com.example.trainticketing.Service.UserServices;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button signin;
    TextView signup;
    DBHelper db;

    AlertDialog.Builder builder2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.usernametxtm);
        password = findViewById(R.id.passwordtxtm);
        signin = findViewById(R.id.signinbtnm);
        signup = findViewById(R.id.signuptxtm);

        db = new DBHelper(this);

        builder2 = new AlertDialog.Builder(this);
        String eml = email.getText().toString();

//        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
//        editor.putString("EMAIL", eml);
//        editor.apply();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                UserModel userModel = new UserModel();
                                userModel.setEmail(email.getText().toString());
                                userModel.setPassword(password.getText().toString());

                                UserServices apiService = ApiUser.getRetrofit().create(UserServices.class);
                                Call call = apiService.userLogin(userModel);
                                editor.putString("nameKey", userModel.getFullname());
                                editor.apply();

                               call.enqueue(new Callback() {
                                    @Override
                                    public void onResponse(Call call, Response response) {
                                        if (response.isSuccessful()) {
                                            editor.putString("emailKey",email.getText().toString());
                                            editor.apply();
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            Log.d("TAG","Response = "+response.code());

                                            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                        } else{
                                            Toast.makeText(getApplicationContext(), "User Deactivated", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

            }
        });

//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UserDB accountDB = new UserDB(getApplicationContext());
//                String uname = email.getText().toString();
//                String pass = password.getText().toString();
//                Account account = accountDB.login(uname, pass);
//
//                if (account == null){
//                    AlertDialog.Builder builder = new  AlertDialog.Builder(view.getContext());
//                    builder.setTitle("Error !!");
//                    builder.setMessage("Invalid Credentials");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.cancel();
//                        }
//                    });
//                    builder.show();
//
//                }else {
//                       editor.putString("emailKey",uname);
//                       editor.apply();
//                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                        intent.putExtra("account", account);
//                        startActivity(intent);
//                        Toast.makeText(MainActivity.this, "Login Success.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String emaill = email.getText().toString();
//                String pass = password.getText().toString();
//                if(email.equals("") || pass.equals(""))
//                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
//                else{
//                    Boolean checkemailpass = db.checkemailpassword(emaill,pass);
//                    if(checkemailpass==true){
//                        editor.putString("emailKey",emaill);
//                        editor.apply();
//                        Toast.makeText(MainActivity.this, "Sign in success", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                        startActivity(i);
//                    }else{
//                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Signup.class);
                startActivity(i);
            }
        });


    }
}