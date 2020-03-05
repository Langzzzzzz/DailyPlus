package com.example.dailyplus.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyplus.Adapter.CustomAdapter;
import com.example.dailyplus.Helper.MyHelper;
import com.example.dailyplus.R;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class bill_fragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private TextView selectDate;
    private DatePickerDialog.OnDateSetListener mDateListener;

    Realm realm;
    RecyclerView recyclerView;
    MyHelper helper;
    RealmChangeListener realmChangeListener;
    CustomAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        helper = new MyHelper(realm);
        helper.selectFromDB();

        adapter = new CustomAdapter(getContext(),helper.justRefresh());

        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                adapter = new CustomAdapter(getContext(),helper.justRefresh());
                recyclerView.setAdapter(adapter);
                if (adapter.getItemCount() > 0)
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        };
        realm.addChangeListener(realmChangeListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container,false);

        selectDate = view.findViewById(R.id.select_date);
        recyclerView = view.findViewById(R.id.bill_cv);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = year + "/" + (month+1) + "/" + dayOfMonth;
        selectDate.setText(date);
    }




}