package com.example.menu.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Calculator extends BottomSheetDialogFragment {
//    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.bottom_sheet_calculator);
////    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.bottom_sheet_calculator, container,false);
            return v;
    }
}

