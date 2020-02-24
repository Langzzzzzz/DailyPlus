package com.example.menu.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Calculator extends BottomSheetDialogFragment implements IBottomSheetCalculatorListener {


    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;
    private Button mButton0;
    private Button mButtonDot;
    private Button mButtonMin;
    private Button mButtonAdd;
    private Button mButtonDel;
    private Button mButtonEnt;

    private EditText locationText;
    private EditText amountText;
    private EditText dateText;

    private boolean isZero = true;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_calculator, container,false);

        mButton1 = v.findViewById(R.id.button1);
        mButton2 = v.findViewById(R.id.button2);
        mButton3 = v.findViewById(R.id.button3);
        mButton4 = v.findViewById(R.id.button4);
        mButton5 = v.findViewById(R.id.button5);
        mButton6 = v.findViewById(R.id.button6);
        mButton7 = v.findViewById(R.id.button7);
        mButton8 = v.findViewById(R.id.button8);
        mButton9 = v.findViewById(R.id.button9);
        mButton0 = v.findViewById(R.id.button0);
        mButtonDot = v.findViewById(R.id.button_dot);
        mButtonMin = v.findViewById(R.id.button_min);
        mButtonAdd = v.findViewById(R.id.button_add);
        mButtonDel = v.findViewById(R.id.button_del);
        mButtonEnt = v.findViewById(R.id.button_ent);

        locationText = v.findViewById(R.id.location_info);
        amountText = v.findViewById(R.id.amount);
        dateText = v.findViewById(R.id.date);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 1 do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("1");
                    isZero = false;
                }else{
                    amountText.append("1");
                }

            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 2 do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("2");
                    isZero = false;
                }else{
                    amountText.append("2");
                }
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 3 do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("3");
                    isZero = false;
                }else{
                    amountText.append("3");
                }
            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 4 do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("4");
                    isZero = false;
                }else{
                    amountText.append("4");
                }
            }
        });
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 5 do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("5");
                    isZero = false;
                }else{
                    amountText.append("5");
                }
            }
        });
        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 6 do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("6");
                    isZero = false;
                }else{
                    amountText.append("6");
                }
            }
        });
        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 7 do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("7");
                    isZero = false;
                }else{
                    amountText.append("7");
                }
            }
        });
        mButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 8 do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("8");
                    isZero = false;
                }else{
                    amountText.append("8");
                }
            }
        });
        mButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 9 do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("9");
                    isZero = false;
                }else{
                    amountText.append("9");
                }
            }
        });
        mButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button 0 do...
                if (!isZero){
                    amountText.append("0");
                }
            }
        });
        mButtonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button . do...
                if (isZero) {
                    amountText.setText("");
                    amountText.append("2");
                }else{
                    amountText.append("2");
                }
            }
        });
        mButtonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button delete do...
            }
        });
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button add do...
            }
        });
        mButtonMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button mininus do...
            }
        });
        mButtonEnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // button enter do...
            }
        });


        return v;
    }

    @Override
    public void onButtonClicked(String text) {

    }
}

 //   public void onClick(View v){
//            switch (v.getId()) {
//                case  R.id.button1: {
//                    // do something for button a click
//
//                    break;
//                }
//
//                case R.id.button2: {
//                    // do something for button b click
//
//                    break;
//                }
//                case  R.id.button3: {
//                    // do something for button c click
//
//                    break;
//                }
//
//                case R.id.button4: {
//                    // do something for button d click
//                    break;
//                }
//
//                case  R.id.button5: {
//                    // do something for button e click
//
//                    break;
//                }
//
//                case R.id.button6: {
//                    // do something for button prev click
//                    break;
//                }
//
//                case  R.id.button7: {
//                    // do something for button submit click
//                    break;
//                }
//
//                case R.id.button8: {
//                    // do something for button next click
//                    break;
//                }
//                case R.id.button9: {
//                    // do something for button prev click
//                    break;
//                }
//
//                case  R.id.button0: {
//                    // do something for button submit click
//                    break;
//                }
//
//                case R.id.button_add: {
//                    // do something for button next click
//                    break;
//                }
//                case R.id.button_min: {
//                    // do something for button next click
//                    break;
//                }
//                case R.id.button_del: {
//                    // do something for button prev click
//                    break;
//                }
//                case R.id.button_ent: {
//                    // do something for button next click
//                    break;
//                }
//
//                case  R.id.button_dot: {
//                    // do something for button submit click
//                    break;
//                }
//
//
//                //.... etc
//                default:
//                    throw new IllegalStateException("Unexpected value: " + v.getId());
//            }
//        }
