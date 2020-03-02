package com.example.dailyplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.dailyplus.Object.Expense;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    Realm realm;

    EditText editAmountDetail;
    Button update, delete;

    Expense expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        realm = Realm.getDefaultInstance();
        editAmountDetail = findViewById(R.id.edit_nameDetail);
        update = findViewById(R.id.btn_update);
        delete = findViewById(R.id.btn_delete);

        Intent getIntent = getIntent();
        final int position = getIntent.getIntExtra("numPosition", 0);

        expense = realm.where(Expense.class).equalTo("expense_id", position).findFirst();

        editAmountDetail.setText(expense.getExpense_amount().toString());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                onBackPressed();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                Intent intent = new Intent();
                intent.putExtra("Delete Position", position);
                onBackPressed();
            }
        });
    }

    private void deleteData(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                expense.deleteFromRealm();
            }
        });
    }

    private void  updateData(){
        Double editedAmount = Double.parseDouble(editAmountDetail.getText().toString());
        realm.beginTransaction();
        expense.setExpense_amount(editedAmount);
        realm.commitTransaction();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
