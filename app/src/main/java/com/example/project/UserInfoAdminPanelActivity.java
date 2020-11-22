package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoAdminPanelActivity extends AppCompatActivity {
    DatabaseConnection db;
    TextView name;
    TextView surname;
    TextView email;
    TextView phone;
    TextView role;
    TextView status;
    Cursor cursor;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_admin_panel);
        db = new DatabaseConnection(this);
        intent = getIntent();
        initialize();
        int id = intent.getIntExtra("EXTRA_ID", 1);
        Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
        cursor = db.fetchUserInfo(id);
        if (cursor.moveToNext()){
            name.setText(cursor.getString(1));
            surname.setText(cursor.getString(2));
            email.setText(cursor.getString(3));
            phone.setText(cursor.getString(4));
            role.setText(cursor.getString(5));
            status.setText(cursor.getString(6));
        }
    }
    void initialize(){
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        role = findViewById(R.id.role);
        status = findViewById(R.id.status);

    }
}