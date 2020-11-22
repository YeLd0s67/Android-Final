package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.widget.TextView;

public class UserInterfaceActivity extends AppCompatActivity {
    DatabaseConnection db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        Intent intent = getIntent();
        TextView ename = (TextView) findViewById(R.id.name);
        TextView epwd = (TextView) findViewById(R.id.pwd);
        String name = intent.getStringExtra("NAME");
        String password = intent.getStringExtra("PWD");
        ename.setText(name);
        epwd.setText(password);
    }
}