package com.example.menu;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
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

public class loadingPage extends AppCompatActivity {
    private static final String TAG  = "loadingPage";
    private FirebaseApp mAuth = FirebaseApp.getInstance();
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
    private EditText passwordInput;
    MaterialTextField materialTextField;
    public static String password;
    public static DB db;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB(this);
        password = db.queryPassword();
        setContentView(R.layout.loading);
        passwordInput = findViewById(R.id.numberPassword);
        materialTextField = findViewById(R.id.mat);
        hidePasswordInputField();//input does not show up unless user creates a password

        login = findViewById(R.id.jumpButton);
        login.setOnClickListener(v->
        {
            Log.v(TAG,"Unlock button clicked");
            if (passwordInput.getText().toString().equals(password) || password.equals("")) {
                Intent nextScreen = new Intent(loadingPage.this, MainActivity.class);
                nextScreen.putExtra("user", "default");
                startActivity(nextScreen);
                finish();
            } else {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
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
        // Check if user is signed in (non-null) and update UI accordingly.
        updateUI(currentUser);
    }
    public void updateUI(FirebaseUser currentUser){
        Log.v(TAG,"User logged in");
//        Toast.makeText(loadingPage.this,"You just login",Toast.LENGTH_LONG).show();
    }
    public void hidePasswordInputField(){
        if (password.equals("")) {
            passwordInput.setVisibility(View.INVISIBLE);
            materialTextField.setVisibility(View.INVISIBLE);

        }
    }


}

