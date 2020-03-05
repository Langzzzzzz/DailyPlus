package com.example.dailyplus.Helper;

import com.example.dailyplus.Object.Expense;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyHelper {

    Realm realm;
    RealmResults<Expense> expenses;

    public MyHelper(Realm realm){
        this.realm = realm;
    }

    public void selectFromDB(){
        expenses = realm.where(Expense.class).findAll();
    }

    public ArrayList<Expense> selectFromDBDate(int year, int month, int day){
        ArrayList<Expense> listItem = new ArrayList<>();
        for (Expense e: expenses){

        }
        return listItem;
    }

    public ArrayList<Expense> justRefresh(){
        ArrayList<Expense> listItem = new ArrayList<>();
        for(Expense e: expenses){
            listItem.add(e);
        }
        return listItem;
    }


}