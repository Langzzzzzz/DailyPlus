package com.example.menu;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
import com.example.menu.database.DB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static android.widget.Toast.LENGTH_LONG;

public class  loadingPage extends AppCompatActivity {
    public static int SLASH = 3000;//waiting time

    private FirebaseApp mAuth = FirebaseApp.getInstance();
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    private EditText passwordInput;

    public static String password;
    public static DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB(this);
        password = db.queryPassword();
        setContentView(R.layout.loading);
        passwordInput = findViewById(R.id.password);

        Button login = findViewById(R.id.jumpButton);
        login.setOnClickListener(v->
        {

            if (passwordInput.getText().toString().equals(password) || password.equals("")) {
                Intent nextScreen = new Intent(loadingPage.this, MainActivity.class);
                nextScreen.putExtra("user", "default");
                startActivity(nextScreen);
                finish();
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser currentUser){
        Toast.makeText(loadingPage.this,"You just login",Toast.LENGTH_LONG).show();
    }


}

