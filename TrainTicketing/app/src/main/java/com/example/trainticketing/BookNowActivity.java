package com.example.trainticketing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainticketing.Api.ApiBooking;
import com.example.trainticketing.Model.BookingModel;
import com.example.trainticketing.Service.BookingService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookNowActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    Button dateButton, back, book;
    TextView txtName, from, to;
    EditText passEdt;
    AlertDialog.Builder builder2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        initDatePicker();
        dateButton = findViewById(R.id.buttonDate);
        back = findViewById(R.id.backbtn);
        txtName = findViewById(R.id.nametxt);
        from = findViewById(R.id.textView8);
        to = findViewById(R.id.textView10);
        passEdt = findViewById(R.id.passedittxt);
        book = findViewById(R.id.booknowbtn);
        builder2 = new AlertDialog.Builder(this);

        dateButton.setText(getTodaysDate());

        String nam = getIntent().getStringExtra("name");
        String frm = getIntent().getStringExtra("from");
        String too = getIntent().getStringExtra("to");
//        String email = PreferenceManager.getDefaultSharedPreferences(this).getString("EMAIL", "");


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email = preferences.getString("emailKey", "");

        txtName.setText(nam);
        from.setText(frm);
        to.setText(too);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder2.setMessage("Do You Want To Book Now?")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                BookingModel bookingModel = new BookingModel();
                                bookingModel.setEmail(email);
                                bookingModel.setName(nam);
                                bookingModel.setNumberOfSeat(Integer.parseInt(passEdt.getText().toString()));
                                bookingModel.setFrom(frm);
                                bookingModel.setTo(too);
                                bookingModel.setDate(dateButton.getText().toString());

                                BookingService apiService = ApiBooking.getRetrofit().create(BookingService.class);
                                Call call = apiService.addBooking(bookingModel);
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onResponse(Call call, Response response) {
                                        if (response.isSuccessful()) {
                                            finish();
                                            Intent intent = new Intent(BookNowActivity.this, MyBookingActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            Log.d("TAG","Response = "+response.code());

                                            Toast.makeText(getApplicationContext(), "Booking Success", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getApplicationContext(), "Maximum Bookings Exceeded", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Booking Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Booking Cancelled",
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateBookingActivity.class);
                startActivity(i);
            }
        });
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}