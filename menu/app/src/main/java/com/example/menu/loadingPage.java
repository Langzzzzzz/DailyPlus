package com.example.menu;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_LONG;

public class loadingPage extends AppCompatActivity {
    public static int SLASH = 3000;//waiting time
    private CallbackManager mCallbackManager;
    private FirebaseApp mAuth = FirebaseApp.getInstance();
    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Cancel", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Error", "facebook:onError", error);
                // ...
            }
        });
// ...

//        Button login = findViewById(R.id.log);
//        login.setOnClickListener(v->
//        {
//            Intent nextScreen = new Intent(loadingPage.this, MainActivity.class);
//            startActivity(nextScreen);
//            finish();
//
//        });

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
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

        updateUI(currentUser);
    }
    public void updateUI(FirebaseUser currentUser){
        //do something with user then
        Toast.makeText(loadingPage.this,"You just login",Toast.LENGTH_LONG).show();
    }


}

