package com.example.dailyplus.Object;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Expense extends RealmObject {

    @PrimaryKey
    private int expense_id;

    private Double expense_amount;

    private String catgory, date;

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public Double getExpense_amount() {
        return expense_amount;
    }

    public void setCatgory(String catgory){this.catgory = catgory;}

    public String getCatgory(){return this.catgory;}

    public void setExpense_amount(Double expense_amount) {
        this.expense_amount = expense_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
