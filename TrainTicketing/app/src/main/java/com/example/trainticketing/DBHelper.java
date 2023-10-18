package com.example.trainticketing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper( Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(fullname TEXT, nic TEXT, phone TEXT, email TEXT, password TEXT, status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String fullname,String nic ,String phone, String email,String password, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullname", fullname);
        cv.put("nic", nic);
        cv.put("phone", phone);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("status", status);

        long result = db.insert("users", null, cv);
        if(result ==-1) return  false;
        else
            return true;
    }

    public Boolean checkemail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email = ?", new String[] {email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkemailpassword(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email = ? and password = ? and status = 'true'", new String[] {email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }




}
