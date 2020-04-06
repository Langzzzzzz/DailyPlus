package com.example.menu;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
import com.github.florent37.materialtextfield.MaterialTextField;
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
//    private CallbackManager mCallbackManager;
    private FirebaseApp mAuth = FirebaseApp.getInstance();
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    private EditText passwordInput;
    MaterialTextField materialTextField;
    public static String password;
    public static DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB(this);
        password = db.queryPassword();
        setContentView(R.layout.loading);
        passwordInput = findViewById(R.id.numberPassword);
        materialTextField = findViewById(R.id.mat);
        if (password.equals("")) {
            passwordInput.setVisibility(View.INVISIBLE);
            materialTextField.setVisibility(View.INVISIBLE);

        }
        //should fix this by first time launch !


        Button login = findViewById(R.id.jumpButton);
        login.setOnClickListener(v->
        {
            if (passwordInput.getText().toString().equals(password) || password.equals("")) {
                Intent nextScreen = new Intent(loadingPage.this, MainActivity.class);
                nextScreen.putExtra("user", "default");
                startActivity(nextScreen);
                finish();
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        });

//        firstLaunch();
//        new Handler().postDelayed(() -> {
//            Intent nextScreen = new Intent(loadingPage.this, MainActivity.class);
//            startActivity(nextScreen);
//            finish();
//            //should load first pic
//        }, SLASH);
    }
//
//    private void firstLaunch() {
//        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun",0);
//        Boolean first_run = sharedPreferences.getBoolean("First",true);
//        if (first_run){
//            sharedPreferences.edit().putBoolean("First",false).apply();
//            //first run => launch tutorial
//        }
//        //else start app as usuals
//
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    // Pass the activity result back to the Facebook SDK
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

        updateUI(currentUser);
    }
    public void updateUI(FirebaseUser currentUser){
        //do something with user then
//        Toast.makeText(loadingPage.this,"You just login",Toast.LENGTH_LONG).show();
    }


}

