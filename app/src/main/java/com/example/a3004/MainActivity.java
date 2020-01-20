package com.example.a3004;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstLaunch();
    }

    private void firstLaunch() {
        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun",0);
        Boolean first_run = sharedPreferences.getBoolean("First",true);
        if (first_run){
            sharedPreferences.edit().putBoolean("First",false).apply();
            Toast.makeText(this,"First Launch",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Not First Launch",Toast.LENGTH_LONG).show();
        }
    }

}
