package com.example.a3004;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class loadingPage extends AppCompatActivity {
    public static int SLASH = 3000;//waiting time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        firstLaunch();
        new Handler().postDelayed(() -> {
            Intent nextScreen = new Intent(loadingPage.this, mainPage.class);
            startActivity(nextScreen);
            finish();
            //should load first pic
        }, SLASH);
    }

    private void firstLaunch() {
        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun",0);
        Boolean first_run = sharedPreferences.getBoolean("First",true);
        if (first_run){
            sharedPreferences.edit().putBoolean("First",false).apply();
            //first run => launch tutorial

        }
        //else start app as usuals

    }

}
