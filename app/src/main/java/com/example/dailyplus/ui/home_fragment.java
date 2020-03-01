package com.example.dailyplus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyplus.Adapter.CustomAdapter;
import com.example.dailyplus.DetailActivity;
import com.example.dailyplus.Helper.MyHelper;
import com.example.dailyplus.MainActivity;
import com.example.dailyplus.Object.Expense;
import com.example.dailyplus.R;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class home_fragment extends Fragment implements CustomAdapter.OnItemListener{

    Realm realm;

    private Button one, two, three, four, five, six, seven, eight, nine, zero, add, sub, equal, set, clear, dot;
    private TextView result;
    private Button cat;

    private final char ADDITION = '+';
    private final char SUBTRACTION = '-';
    private final char EQU = 0;
    private double val1 = Double.NaN;
    private double val2;
    private char ACTION;

    private double answer;

    private boolean isZero = true;

    PopupMenu popupMenu;

    Calendar calendar;
    String formatedDate;

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

        calendar = Calendar.getInstance();
        formatedDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

        adapter = new CustomAdapter(getContext(), helper.justRefresh(),this);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        one = view.findViewById(R.id.b1);
        two = view.findViewById(R.id.b2);
        three = view.findViewById(R.id.b3);
        four = view.findViewById(R.id.b4);
        five = view.findViewById(R.id.b5);
        six = view.findViewById(R.id.b6);
        seven = view.findViewById(R.id.b7);
        eight = view.findViewById(R.id.b8);
        nine = view.findViewById(R.id.b9);
        zero = view.findViewById(R.id.b0);
        dot = view.findViewById(R.id.button_dot);
        add = view.findViewById(R.id.plus);
        sub = view.findViewById(R.id.bmin);
        set = view.findViewById(R.id.bOK);
        clear = view.findViewById(R.id.bclear);
        cat = view.findViewById(R.id.category);

        result = view.findViewById(R.id.textView);

        realm = Realm.getDefaultInstance();
        recyclerView = view.findViewById(R.id.textView2);

        result.setText("0");

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isZero){
                    result.append("0");
                }
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZero) {
                    result.setText("");
                    result.append("1");
                    isZero = false;
                }else{
                    result.append("1");
                }
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZero) {
                    result.setText("");
                    result.append("2");
                    isZero = false;
                }else{
                    result.append("2");
                }
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZero) {
                    result.setText("");
                    result.append("3");
                    isZero = false;
                }else{
                    result.append("3");
                }
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZero) {
                    result.setText("");
                    result.append("4");
                    isZero = false;
                }else{
                    result.append("4");
                }
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZero) {
                    result.setText("");
                    result.append("5");
                    isZero = false;
                }else{
                    result.append("5");
                }
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZero) {
                    result.setText("");
                    result.append("6");
                    isZero = false;
                }else{
                    result.append("6");
                }
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZero) {
                    result.setText("");
                    result.append("7");
                    isZero = false;
                }else{
                    result.append("7");
                }
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZero) {
                    result.setText("");
                    result.append("8");
                    isZero = false;
                }else{
                    result.append("8");
                }
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZero) {
                    result.setText("");
                    result.append("9");
                    isZero = false;
                }else{
                    result.append("9");
                }
            }
        });

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.append(".");
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isZero) {
                    compute();
                    ACTION = SUBTRACTION;
                    result.append("-");
                }
            }
        });
//
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isZero) {
                    compute();
                    ACTION = ADDITION;
                    result.append("+");
                }
            }
        });


//
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isZero) {
                    compute();
                    ACTION = EQU;
                    //info.setText(result.getText().toString() + String.valueOf(val2) + "=" + String.valueOf(val1));
                    // 5 + 4 = 9
                    //result.setText(String.valueOf(val1));
                    answer = val1;
                    saveData();
                    Refresh();
                    result.setText("");
                    val1 = Double.NaN;
                    val2 = Double.NaN;
                    isZero = true;
                }
            }
        });
//
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String display = result.getText().toString();
                int len = display.length();

                if (len > 0){
                    display = display.substring(0, len-1);
                    result.setText(display);
                }else{
                    isZero = true;
                }
            }
        });

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void compute(){
        if(!Double.isNaN(val1)){
            String display = result.getText().toString();
            int opAt = 0;
            for (int i = 0; i < display.length(); i++){
                if (display.charAt(i) == '+' || display.charAt(i) == '-')
                    opAt = i;
            }
            val2 = Double.parseDouble(result.getText().toString().substring(opAt, display.length()));

            switch(ACTION){
                case ADDITION:
                    val1 = val1 + val2;
                    break;
                case SUBTRACTION:
                    val1 = val1 - val2;
                    break;
                case EQU:
                    break;
            }
        }
        else{
            val1 = Double.parseDouble(result.getText().toString());
        }
    }

    private void saveData(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Number maxId = bgRealm.where(Expense.class).max("expense_id");

                int newKey = (maxId == null) ? 1 : maxId.intValue()+1;

                Expense expense = bgRealm.createObject(Expense.class, newKey);
                expense.setExpense_amount(answer);
                expense.setCatgory(cat.getText().toString());
                expense.setDate(formatedDate);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_LONG).show();
                // Transaction failed and was automatically canceled.
            }
        });
    }

    private void Refresh(){
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                adapter = new CustomAdapter(getContext(),helper.justRefresh());
                recyclerView.setAdapter(adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }

    @Override
    public void onItemClick(int position) {
        Refresh();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.food:
                        cat.setText("Food");
                        return true;

                    case R.id.snack:
                        cat.setText("snack");
                        return true;

                    case R.id.eatOut:
                        cat.setText("Eat Out");
                        return true;

                    case R.id.rent:
                        cat.setText("Rent");
                        return true;

                    case R.id.drink:
                        cat.setText("Drink");
                        return true;

                    case R.id.groceries:
                        cat.setText("Groceries");
                        return true;

                    case R.id.taxi:
                        cat.setText("Taxi");
                        return true;
                 }
                return false;
            }
        });
    }



}
