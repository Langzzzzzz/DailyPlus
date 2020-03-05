package com.example.dailyplus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyplus.DetailActivity;
import com.example.dailyplus.Object.Expense;
import com.example.dailyplus.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<MyViewHolder>{
    Context c;
    ArrayList<Expense> expenses;

    private OnItemListener mOnItemListener;

    public CustomAdapter(Context c, ArrayList<Expense> expenses, OnItemListener onItemListener){
        this.c = c;
        this.expenses = expenses;
        mOnItemListener = onItemListener;
    }

    public CustomAdapter(Context c, ArrayList<Expense> expenses){
        this.c = c;
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Expense e = expenses.get(position);
        holder.txtAmount.setText(e.getExpense_amount().toString());
        holder.txtCat.setText(e.getCatgory());
        holder.txtDate.setText(e.getDate());
        holder.setOnItemListener(new OnItemListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("under:" + position);
                Intent intent= new Intent(c, DetailActivity.class);
                System.out.println("click");
                System.out.println(position);

                Expense e = (Expense) getItem(position);
                final int numPosition = e.getExpense_id();
                intent.putExtra("numPosition", numPosition);
                c.startActivity(intent);

                Intent getintent = new Intent();

                int delete_position = getintent.getIntExtra("Delete Position", -1);

                if (delete_position != -1){
                    notifyItemRemoved(delete_position);
                    System.out.println("de"+delete_position);
                }
            }
        });
        System.out.println("bine" + position);

    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public Expense getItem(int posi){return expenses.get(posi);}

    public interface OnItemListener{
        void onItemClick(int position);
    }

}
