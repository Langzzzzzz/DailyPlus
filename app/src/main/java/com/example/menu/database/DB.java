package com.example.menu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.menu.data.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {

    private static final String DB_NAME = "daily+.db";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "transactions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CATEGORY = "category";

    private static final String TABLE_CATEGORY_NAME = "categories";
    private static final String COLUMN_ENGLISH = "English";
    private static final String COLUMN_CHINESE = "Chinese";

    public DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_ID + " TEXT NOT NULL," +
                COLUMN_AMOUNT + " DOUBLE NOT NULL," +
                COLUMN_LOCATION + " TEXT," +
                COLUMN_DATE + " DATE NOT NULL," +
                COLUMN_CATEGORY + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE);
        String CATEGORY_CREATE = "CREATE TABLE " + TABLE_CATEGORY_NAME + "(" +
                COLUMN_ENGLISH + " TEXT PRIMARY KEY," +
                COLUMN_CHINESE + " TEXT NOT NULL);";
        db.execSQL(CATEGORY_CREATE);
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENGLISH, "food");
        values.put(COLUMN_CHINESE, "食物");
        db.insert(TABLE_CATEGORY_NAME, null, values);
        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_ENGLISH, "traffic");
        values2.put(COLUMN_CHINESE, "交通");
        db.insert(TABLE_CATEGORY_NAME, null, values2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertNewTransaction(String userid, Transaction transaction) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, transaction.getCategory().toString());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_DATE, transaction.getDateString());
        values.put(COLUMN_LOCATION, transaction.getLocation());
        values.put(COLUMN_USER_ID, userid);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public long insertNewCategory(String category, String Chinese) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENGLISH, category);
        values.put(COLUMN_CHINESE, Chinese);
        long result = db.insert(TABLE_CATEGORY_NAME, null, values);
        db.close();
        return result;
    }

    public List<Transaction> queryAllTransactions(String userid) {
        SQLiteDatabase db = getReadableDatabase();
        List<Transaction> transactions = new ArrayList<>();
        String sql_query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + "='" + userid + "';";
        Cursor cursor = db.rawQuery(sql_query, null);
        while (cursor.moveToNext()) {
            Transaction transaction = new Transaction(cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
//            System.out.println(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)) + " " + cursor.getColumnIndex(COLUMN_ID));
            transaction.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            transactions.add(transaction);
        }
        db.close();
        return transactions;
    }

    public List<String> queryAllCategories(boolean English) {
        SQLiteDatabase db = getReadableDatabase();
        List<String> categories = new ArrayList<>();
        String sql_query = "SELECT * FROM " + TABLE_CATEGORY_NAME;
        Cursor cursor = db.rawQuery(sql_query, null);
        while (cursor.moveToNext()) {
            if (English)
                categories.add(cursor.getString(0));
            else
                categories.add(cursor.getString(1));
        }
        db.close();
        return categories;
    }

    public void deleteCategory(String category) {
        String sql_delete = "DELETE FROM " + TABLE_CATEGORY_NAME + " WHERE " + COLUMN_ENGLISH + "='" + category + "'";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql_delete);
        db.close();
    }

    public void deleteTransaction(int id) {
        String sql_delete = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id + ";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql_delete);
        db.close();
    }
}
