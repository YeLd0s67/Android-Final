package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    DatabaseConnection db;
    EditText email;
    EditText password;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pwd);
        checkBox = findViewById(R.id.checkbox);
        db = new DatabaseConnection(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }
    public void Register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void onClickLog(View view){
        Cursor c;
        try {
            c = db.checkUser(email.getText().toString(), password.getText().toString());
            if (c.moveToFirst()){
                if (c.getString(c.getColumnIndex("EMAIL")).equals(email.getText().toString())){
                    if (c.getString(c.getColumnIndex("PASSWORD")).equals(password.getText().toString())){
                        if (c.getString(c.getColumnIndex("ROLE")).equals("user")){
                            Toast.makeText(getApplicationContext(), "USER FOUND", Toast.LENGTH_SHORT).show();
                            c.close();
                            Intent intent2 = new Intent(this, UserInterfaceActivity.class);
                            intent2.putExtra("EMAIL", email.getText().toString());
                            intent2.putExtra("PWD", password.getText().toString());
                            startActivity(intent2);
                        }
                        if (c.getString(c.getColumnIndex("ROLE")).equals("admin")){
                            Toast.makeText(getApplicationContext(), "ADMIN FOUND", Toast.LENGTH_SHORT).show();
                            c.close();
                            Intent intent3 = new Intent(this, AdminActivity.class);
                            startActivity(intent3);
                        }

                    }
                }
            }else{
                Toast.makeText(getApplicationContext(), "USER NOT FOUND", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "USER NOT FOUND", Toast.LENGTH_LONG).show();
        }

    }
}