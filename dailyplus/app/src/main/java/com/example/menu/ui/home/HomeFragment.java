package com.example.menu.ui.home;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.menu.MainActivity;
import com.example.menu.R;
import com.example.menu.data.Language;
import com.example.menu.data.Transaction;
import com.example.menu.loadingPage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private List<String> transactionTags;
    private List<Transaction> transactions;
    private List<Double> incomes;
    private List<Double> expenses;
    public static TransactionListAdapter adapter;

    private TextView homeYear;
    private TextView homeMonth;
    private TextView homeMonthLabel;
    private TextView homeIncomeLabel;
    private TextView homeExpenseLabel;
    private TextView homeIncome;
    private TextView homeExpense;
    private ImageView homeDropDown;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeYear = root.findViewById(R.id.home_year);
        homeMonth = root.findViewById(R.id.home_month);
        homeMonthLabel = root.findViewById(R.id.home_month_label);
        homeIncomeLabel = root.findViewById(R.id.home_income_label);
        homeExpenseLabel = root.findViewById(R.id.home_expense_label);
        homeIncome = root.findViewById(R.id.home_income);
        homeExpense = root.findViewById(R.id.home_expense);
        homeDropDown = root.findViewById(R.id.home_dropdown);

        homeMonthLabel.setText(Language.month());
        homeIncomeLabel.setText(Language.income());
        homeExpenseLabel.setText(Language.expense());

        homeMonth.setOnClickListener(this);
        homeMonthLabel.setOnClickListener(this);
        homeDropDown.setOnClickListener(this);

        Calendar ca = Calendar.getInstance();
        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        homeYear.setText(mYear + Language.year());
        homeMonth.setText(mMonth < 9 ? "0" + (mMonth + 1) : (mMonth + 1) + "");
        String dateString = mYear + "-" + (mMonth + 1);
        if (mMonth < 9) {
            dateString = mYear + "-0" + (mMonth + 1);
        }

        transactions = new ArrayList<>();
        double totalIncome = 0;
        double totalExpense = 0;
        for (Transaction transaction : MainActivity.transactions) {
            if (transaction.getDateString().contains(dateString)) {
                if (transaction.getAmount() < 0)
                    totalExpense += transaction.getAmount();
                else
                    totalIncome += transaction.getAmount();
                transactions.add(transaction);
            }
        }
        DecimalFormat formatter = new DecimalFormat("0.00");
        homeIncome.setText(formatter.format(totalIncome));
        homeExpense.setText(formatter.format(totalExpense));
        listView = root.findViewById(R.id.transaction_list);
        transactionTags = new ArrayList<>();
        expenses = new ArrayList<>();
        incomes = new ArrayList<>();
        Collections.sort(transactions, (transaction, t1) -> t1.getDate().compareTo(transaction.getDate()));
        setData();
        adapter = new TransactionListAdapter(getActivity(), transactions, transactionTags);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            System.out.println("long lick " + adapter.getItem(position).getId());
            loadingPage.db.deleteTransaction(adapter.getItem(position).getId());
            MainActivity.transactions.remove(adapter.getItem(position));
            adapter.notifyDataSetChanged();
            return true;
        });
        return root;
    }


    private void setData() {
        if (transactions.size() > 0) {
            transactionTags.add(transactions.get(0).getDateString());
            double income = 0;
            double expense = 0;
            transactions.add(0, new Transaction(0, null, transactions.get(0).getDateString(), null));
            for (int i = 1; i < transactions.size();) {
                if (transactions.get(i).getDateString().equals(transactionTags.get(transactionTags.size() - 1))) {
                    if (transactions.get(i).getAmount() < 0) {
                        expense += transactions.get(i).getAmount();
                    } else {
                        income += transactions.get(i).getAmount();
                    }
                    i++;
                    continue;
                } else {
                    incomes.add(income);
                    expenses.add(expense);
                    income = 0;
                    expense = 0;
                    transactionTags.add(transactions.get(i).getDateString());
                    transactions.add(i, new Transaction(0, null, transactions.get(i).getDateString(), null));
                }
            }
            incomes.add(income);
            expenses.add(expense);
        }
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog dlg = new DatePickerDialog(new ContextThemeWrapper(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar), null, Integer.valueOf(homeYear.getText().toString().substring(0, 4)), Integer.valueOf(homeMonth.getText().toString()), 0) {
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
//                String dateString = year + "-" + (month + 1);
//                if (month < 9) {
//                    dateString = year + "-0" + (month + 1);
//                }
                homeYear.setText(year + Language.year());
                homeMonth.setText(month < 9 ? "0" + (month + 1) : month + "");
                adapter.notifyDataSetChanged();
            }
        };
        dlg.setTitle(Language.monthChooseTitle());
        dlg.show();
    }

    private class TransactionListAdapter extends ArrayAdapter<Transaction> {

        public TransactionListAdapter(Context context, List<Transaction> transactions, List<String> tags) {
            super(context, 0, transactions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(getItem(position).getCategory() == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.transaction_item_tag, null);
                TextView dateTextView = view.findViewById(R.id.date_tag);
                TextView amountTextView = view.findViewById(R.id.amount_tag);
                dateTextView.setText(getItem(position).getDateString());
                StringBuilder builder = new StringBuilder(Language.expense() + ": ");
                builder.append(expenses.get(transactionTags.indexOf(getItem(position).getDateString())));
                builder.append(" " + Language.income() + ": ");
                builder.append(incomes.get(transactionTags.indexOf(getItem(position).getDateString())));
                amountTextView.setText(builder.toString());
            } else {
                view = LayoutInflater.from(getContext()).inflate(R.layout.transaction_item, null);
                ImageView image = view.findViewById(R.id.item_pic);
                TextView description = view.findViewById(R.id.item_description);
                TextView amount = view.findViewById(R.id.item_amount);
                if (getItem(position).getCategory().toLowerCase().equals("food")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.food, null));
                } else if (getItem(position).getCategory().toLowerCase().equals("traffic")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.traffic, null));
                }  else if (getItem(position).getCategory().toLowerCase().equals("gas")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.gas, null));
                }  else if (getItem(position).getCategory().toLowerCase().equals("gym")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.gym, null));
                }  else if (getItem(position).getCategory().toLowerCase().equals("house")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.house, null));
                }  else if (getItem(position).getCategory().toLowerCase().equals("income")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.income, null));
                }  else if (getItem(position).getCategory().toLowerCase().equals("insurance")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.insurance, null));
                }  else if (getItem(position).getCategory().toLowerCase().equals("pet")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.pet, null));
                }  else if (getItem(position).getCategory().toLowerCase().equals("shopping")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.shopping, null));
                }  else if (getItem(position).getCategory().toLowerCase().equals("tele")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.tele, null));
                }  else if (getItem(position).getCategory().toLowerCase().equals("travel")) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.travel, null));
                } else {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.others, null));
                }
                description.setText(getItem(position).getCategory());
                amount.setText(getItem(position).getAmount() + "");
            }
            return view;
        }

        @Override
        public boolean isEnabled(int position) {
            if (getItem(position).getCategory() == null) {
                return false;
            }
            return super.isEnabled(position);
        }

        @Override
        public void notifyDataSetChanged() {
            transactions.clear();
            String dateString = homeYear.getText().toString().substring(0, 4) + "-" + homeMonth.getText().toString();
            double totalIncome = 0;
            double totalExpense = 0;
            for (Transaction transaction : MainActivity.transactions) {
                if (transaction.getDateString().contains(dateString)) {
                    if (transaction.getAmount() < 0)
                        totalExpense += transaction.getAmount();
                    else
                        totalIncome += transaction.getAmount();
                    transactions.add(transaction);
                }
            }
            DecimalFormat formatter = new DecimalFormat("0.00");
            homeIncome.setText(formatter.format(totalIncome));
            homeExpense.setText(formatter.format(totalExpense));
            transactionTags.clear();
            incomes.clear();
            expenses.clear();
            Collections.sort(transactions, (transaction, t1) -> t1.getDate().compareTo(transaction.getDate()));
            setData();
            super.notifyDataSetChanged();
        }
    }
}