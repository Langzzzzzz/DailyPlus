package com.example.menu.ui.chart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.menu.MainActivity;
import com.example.menu.R;
import com.example.menu.data.Language;
import com.example.menu.data.PieView;
import com.example.menu.data.Statistics;

public class ChartFragment extends Fragment {

    private LinearLayout incomeLayout;
    private LinearLayout expenseLayout;
    private TextView incomeChartLabel;
    private TextView expenseChartLabel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chart, container, false);
        incomeLayout = root.findViewById(R.id.income_layout);
        expenseLayout = root.findViewById(R.id.expense_layout);
        incomeChartLabel = root.findViewById(R.id.income_chart_label);
        incomeChartLabel.setText(Language.income());
        expenseChartLabel = root.findViewById(R.id.expense_chart_label);
        expenseChartLabel.setText(Language.expense());
        PieView incomeView = new PieView(getActivity());
        incomeView.setData(Statistics.getIncomeStatisticData(MainActivity.transactions));
        incomeLayout.addView(incomeView);
        PieView expenseView = new PieView(getActivity());
        expenseView.setData(Statistics.getExpenseStatisticData(MainActivity.transactions));
        expenseLayout.addView(expenseView);
        return root;
    }
}
