package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseConnection db;
    EditText name;
    EditText surname;
    EditText email;
    EditText password;
    EditText phone;
    Spinner roles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pwd);
        phone = (EditText) findViewById(R.id.phone);
        roles=(Spinner)findViewById(R.id.role);

        String[] rolesList=getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.spinner, rolesList);
        roles.setAdapter(adapter);
        db = new DatabaseConnection(this);
    }

    public void onClickReg(View view){
        if (name.getText().toString().equals("")||surname.getText().toString().equals("")||email.getText().toString().equals("")||password.getText().toString().equals("")||phone.getText().toString().equals("")){
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_LONG).show();
        }else {
            db.insertUser(name.getText().toString(), surname.getText().toString(), email.getText().toString(), phone.getText().toString(), roles.getSelectedItem().toString(), password.getText().toString());
            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}