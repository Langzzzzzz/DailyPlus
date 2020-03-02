package com.example.dailyplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class loadingScene extends AppCompatActivity {
    public static int SLASH = 3000;//waiting time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_scene);
        new Handler().postDelayed(() -> {
            Intent nextScreen = new Intent(loadingScene.this, MainActivity.class);
            startActivity(nextScreen);
            finish();
            //should load first pic
        }, SLASH);
    }
}