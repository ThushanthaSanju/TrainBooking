package com.example.trainticketing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trainticketing.Model.Account;

import java.util.ArrayList;

public class UserDB extends SQLiteOpenHelper {

    private static  String dbName= "accountDB";
    private static  String tableName= "account";
    private static  String idColumn= "id";
    private static  String fullNameColumn= "fullname";
    private static  String nicColumn= "nic";
    private static  String phoneColumn= "phone";
    private static  String emailColumn= "email";
    private static  String passwordColumn= "password";
    private static  String statusColumn= "status";



    public UserDB(Context context){
        super(context, dbName, null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + tableName + "(" +
                idColumn + " integer primary key autoincrement, " +
                fullNameColumn + " text," +
                nicColumn + " text," +
                phoneColumn + " text," +
                emailColumn + " text," +
                passwordColumn + " text," +
                statusColumn + " text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public boolean create(Account account){
        boolean result = true;
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(fullNameColumn, account.getFullname());
            contentValues.put(nicColumn, account.getNic());
            contentValues.put(phoneColumn, account.getPhone());
            contentValues.put(emailColumn, account.getEmail());
            contentValues.put(passwordColumn, account.getPassword());
            contentValues.put(statusColumn, account.getStatus());
            result = sqLiteDatabase.insert(tableName, null, contentValues) > 0;

        }catch (Exception e){
            result = false;
        }
        return result;
    }

    public boolean update(Account account){
        boolean result = true;
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(fullNameColumn, account.getFullname());
            contentValues.put(nicColumn, account.getNic());
            contentValues.put(phoneColumn, account.getPhone());
            contentValues.put(emailColumn, account.getEmail());
            contentValues.put(passwordColumn, account.getPassword());
            contentValues.put(statusColumn, account.getStatus());
            result = sqLiteDatabase.update(tableName, contentValues, idColumn + " = ?", new String[] { String.valueOf(account.getId())}) > 0;

        }catch (Exception e){
            result = false;
        }
        return result;
    }

    public Account login(String email, String password){
        Account account = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName + " where email = ? and password = ? and status = 'true'", new String[]{email, password});
            if (cursor.moveToFirst()){
                account = new Account();
                account.setId(cursor.getInt(0));
                account.setFullname(cursor.getString(1));
                account.setNic(cursor.getString(2));
                account.setPhone(cursor.getString(3));
                account.setEmail(cursor.getString(4));
                account.setPassword(cursor.getString(5));
                account.setStatus(cursor.getString(6));


            }
        }catch (Exception e){
            account = null;
        }
        return account;
    }

    public Account checkUsername(String username){
        Account account = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName + " where email = ?", new String[]{username});
            if (cursor.moveToFirst()){
                account = new Account();
                account.setId(cursor.getInt(0));
                account.setFullname(cursor.getString(1));
                account.setNic(cursor.getString(2));
                account.setPhone(cursor.getString(3));
                account.setEmail(cursor.getString(4));
                account.setPassword(cursor.getString(5));
                account.setStatus(cursor.getString(6));

            }
        }catch (Exception e){
            account = null;
        }
        return account;
    }


    public Account find(String email){
        Account account = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName + " where email = ?", new String[]{email});
            if (cursor.moveToFirst()){
                account = new Account();
                account.setId(cursor.getInt(0));
                account.setFullname(cursor.getString(1));
                account.setNic(cursor.getString(2));
                account.setPhone(cursor.getString(3));
                account.setEmail(cursor.getString(4));
                account.setPassword(cursor.getString(5));
                account.setStatus(cursor.getString(6));

            }
        }catch (Exception e){
            account = null;
        }
        return account;
    }


}