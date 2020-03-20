package com.example.menu.BottomSheet;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.menu.MainActivity;
import com.example.menu.R;
import com.example.menu.data.Language;
import com.example.menu.data.Transaction;
import com.example.menu.loadingPage;
import com.example.menu.ui.home.HomeFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Calculator extends BottomSheetDialogFragment implements View.OnClickListener {

    private Spinner categoriesSpinner;
    private EditText location;
    private TextView amount;
    private double currentAmount;
    private String currentText;
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
    private List<String> categories;
    private Button mButtonDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_calculator, container, false);

        categoriesSpinner = v.findViewById(R.id.transaction_categories);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item);
        categories = Language.getCatogories();
        for (int i = 0; i < categories.size(); i++) {
            dataAdapter.add(categories.get(i));
        }
        categoriesSpinner.setAdapter(dataAdapter);
        categoriesSpinner.setSelection(0);

        location = v.findViewById(R.id.location_info);

        amount = v.findViewById(R.id.amount);
        currentText = "";
        currentAmount = 0;

        mButtonDate = v.findViewById(R.id.date);
        mButtonDate.setOnClickListener(this);
        mButtonDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()));

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
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton0.setOnClickListener(this);
        mButtonDot.setOnClickListener(this);
        mButtonMin.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);
        mButtonDel.setOnClickListener(this);
        mButtonEnt.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.date) {
            Calendar ca = Calendar.getInstance();
            int mYear = ca.get(Calendar.YEAR);
            int mMonth = ca.get(Calendar.MONTH);
            int mDay = ca.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view, year, month, dayOfMonth) -> {
                        mButtonDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    },
                    mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (v.getId() == R.id.button_ent) {
            currentAmount = calculate(currentText);
            if (categories.get(categoriesSpinner.getSelectedItemPosition()).equals("income")) {
                currentAmount = Math.abs(currentAmount);
            } else {
                currentAmount = - Math.abs(currentAmount);
            }
            Transaction transaction = new Transaction(currentAmount,
                    location.getText() == null ? "" : location.getText().toString(),
                    mButtonDate.getText().toString(),
                    categories.get(categoriesSpinner.getSelectedItemPosition()));
            loadingPage.db.insertNewTransaction(MainActivity.user, transaction);
            MainActivity.transactions.add(transaction);

            ((BaseAdapter)HomeFragment.adapter).notifyDataSetChanged();
            this.dismiss();
        } else if (v.getId() == R.id.button_dot) {
            if (currentText.contains("+") || currentText.contains("-")) {
                String tmpText = currentText.substring(currentText.indexOf(currentText.contains("+") ? "+" : "-"), currentText.length());
                if (!tmpText.contains(".") && tmpText.length() != 0) {
                    currentText += ".";
                }
            } else {
                if (!currentText.contains(".")) {
                    currentText += ".";
                }
            }
        } else if (v.getId() == R.id.button_del) {
            if (currentText.length() > 0){
                currentText = currentText.substring(0, currentText.length() - 1);
            }

        } else if (v.getId() == R.id.button_add || v.getId() == R.id.button_min) {
            if (!currentText.contains("+") && !currentText.contains("-")) {
                currentText += v.getId() == R.id.button_add ? "+" : "-";
            } else {
                if (!currentText.endsWith("+") && !currentText.endsWith("-")) {
                    currentAmount = calculate(currentText);
                    if (currentAmount % 1 == 0) {
                        currentText = (int)currentAmount + (v.getId() == R.id.button_add ? "+" : "-");
                    } else {
                        currentText = currentAmount + (v.getId() == R.id.button_add ? "+" : "-");
                    }
                }
            }
        } else {
            if (currentText.length() < 24) {
                currentText += ((Button) v).getText();
            }
        }
        amount.setText(currentText);
    }

    private double calculate(String expression) {
        String[] values;
        String first = "";
        if (expression.startsWith("-") || expression.startsWith("-")) {
            first += expression.charAt(0);
            expression = expression.substring(1);
        }
        if (expression.endsWith("-") || expression.endsWith("-")) {
            expression = expression.substring(0, expression.length() - 1);
        }
        if (expression.contains("+") || expression.contains("-")) {
            if (expression.contains("+")) {
                values = expression.split("\\+");
            } else {
                values = expression.split("-");
                values[1] = "-" + values[1];
            }
            double result = Double.valueOf(values[0].equals("") ? "0" : first + values[0]) + Double.valueOf(values[1]);
            return result;
        } else {
            return Double.valueOf(first + expression);
        }
    }
}

