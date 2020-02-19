package com.example.a3004;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class mainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Button vb =findViewById(R.id.addButton);
        vb.setOnClickListener(v->

        {
            //pop up a window AND do something
            //do something here
        });

    }
}
