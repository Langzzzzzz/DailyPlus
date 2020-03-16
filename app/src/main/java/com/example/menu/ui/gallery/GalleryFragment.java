package com.example.menu.ui.gallery;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.menu.MainActivity;
import com.example.menu.R;
import com.example.menu.data.Language;
import com.example.menu.data.PieView;
import com.example.menu.data.Statistics;

import java.util.Calendar;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private LinearLayout incomeLayout;
    private LinearLayout expenseLayout;
    private TextView incomeChartLabel;
    private TextView expenseChartLabel;
    private Button date;
    private ImageView dropdown;
    private PieView incomeView;
    private PieView expenseView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        incomeLayout = root.findViewById(R.id.bill_income_layout);
        expenseLayout = root.findViewById(R.id.bill_expense_layout);
        date = root.findViewById(R.id.bill_date);
        date.setOnClickListener(this);
        dropdown = root.findViewById(R.id.bill_dropdown);
        dropdown.setOnClickListener(this);
        incomeChartLabel = root.findViewById(R.id.bill_income_chart_label);
        incomeChartLabel.setText(Language.income());
        expenseChartLabel = root.findViewById(R.id.bill_expense_chart_label);
        expenseChartLabel.setText(Language.expense());
        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        String dateString = mYear + "-" + (mMonth + 1);
        if (mMonth < 9) {
            dateString = mYear + "-0" + (mMonth + 1);
        }
        date.setText(dateString);
        incomeView = new PieView(getActivity());
        incomeView.setData(Statistics.getIncomeStatisticDataByMonth(MainActivity.transactions, dateString));
        incomeLayout.addView(incomeView);
        expenseView = new PieView(getActivity());
        expenseView.setData(Statistics.getExpenseStatisticDataByMonth(MainActivity.transactions, dateString));
        expenseLayout.addView(expenseView);
        return root;
    }

    @Override
    public void onClick(View v) {
        String[] dateInfo = date.getText().toString().split("-");
        DatePickerDialog dlg = new DatePickerDialog(new ContextThemeWrapper(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar), null, Integer.valueOf(dateInfo[0]), Integer.valueOf(dateInfo[1]), 0) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                LinearLayout mSpinners = findViewById(getContext().getResources().getIdentifier("android:id/pickers", null, null));
                if (mSpinners != null) {
                    NumberPicker monthPickerView = findViewById(getContext().getResources().getIdentifier("android:id/month", null, null));
                    NumberPicker yearPickerView = findViewById(getContext().getResources().getIdentifier("android:id/year", null, null));
                    mSpinners.removeAllViews();
                    if (monthPickerView != null) {
                        mSpinners.addView(monthPickerView);
                    }
                    if (yearPickerView != null) {
                        mSpinners.addView(yearPickerView);
                    }
                }
                View dayPickerView = findViewById(getContext().getResources().getIdentifier("android:id/day", null, null));
                if(dayPickerView != null){
                    dayPickerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                String dateString = year + "-" + (month + 1);
                if (month < 9) {
                    dateString = year + "-0" + (month + 1);
                }
                date.setText(dateString);
                incomeView.setData(Statistics.getIncomeStatisticDataByMonth(MainActivity.transactions, dateString));
//                incomeLayout.addView(incomeView);
                expenseView.setData(Statistics.getExpenseStatisticDataByMonth(MainActivity.transactions, dateString));
//                expenseLayout.addView(expenseView);
            }
        };
        dlg.setTitle(Language.monthChooseTitle());
        dlg.show();
    }
}