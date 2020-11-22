package com.example.project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DatabaseConnection extends SQLiteOpenHelper{
    private static final String DB_NAME = "project";
    private static final int DB_VERSION = 1;
    String TAG = "insert";

    DatabaseConnection(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS ("
                + "_id TEXT PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "SURNAME TEXT, "
                + "EMAIL TEXT, "
                + "PHONE TEXT, "
                + "ROLE TEXT, "
                + "STATUS TEXT DEFAULT ' active', "
                + "PASSWORD TEXT);");
        db.execSQL("CREATE TABLE ADMINS ("
                + "_id TEXT PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "SURNAME TEXT, "
                + "EMAIL TEXT, "
                + "PHONE TEXT, "
                + "ROLE TEXT, "
                + "STATUS TEXT DEFAULT ' active', "
                + "PASSWORD TEXT);");
        db.execSQL("CREATE TABLE MANAGERS ("
                + "_id TEXT PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "SURNAME TEXT, "
                + "EMAIL TEXT, "
                + "PHONE TEXT, "
                + "ROLE TEXT, "
                + "STATUS TEXT DEFAULT ' active', "
                + "PASSWORD TEXT);");
        db.execSQL("CREATE TABLE LIST ("
                + "_id TEXT PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "SURNAME TEXT, "
                + "EMAIL TEXT, "
                + "PHONE TEXT, "
                + "ROLE TEXT, "
                + "STATUS TEXT DEFAULT ' active', "
                + "PASSWORD TEXT);");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertUser(String name, String surname, String email, String phone, String role, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userInfo = new ContentValues();
        userInfo.put("NAME", name);
        userInfo.put("SURNAME", surname);
        userInfo.put("EMAIL", email);
        userInfo.put("PHONE", phone);
        userInfo.put("ROLE", role);
        userInfo.put("PASSWORD", pwd);
        if (role.equals("user")){
            db.insert("USERS", null, userInfo);
            Log.v(TAG, "INSERTED");
            db.execSQL("CREATE TABLE "+name+" ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "TOPIC TEXT, "
                    + "WHO TEXT);");
            Log.v(TAG, "User Table created");
            db.insert("LIST", null, userInfo);
        }
        if (role.equals("admin")){
            db.insert("ADMINS", null, userInfo);
            db.insert("LIST", null, userInfo);
            Log.v(TAG, "INSERTED");
        }
        if (role.equals("manager")){
            db.insert("MANAGERS", null, userInfo);
            db.insert("LIST", null, userInfo);
            Log.v(TAG, "INSERTED");
        }

    }
    public Cursor checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("LIST",
                new String[] {"*"},
                "EMAIL = ? AND PASSWORD = ?",
                new String[] {email, password},
                null, null, null);
        return c;
    }

    public Cursor toListView(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("LIST",
                new String[] {"*"}, null, null, null, null, null
                );
        return c;
    }
    public Cursor fetchUserInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(
                "SELECT * FROM LIST WHERE _id= '"+id+"'" , null);
        return  mCursor;
    }
}
