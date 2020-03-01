package com.example.dailyplus.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyplus.R;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView txtAmount, txtCat, txtDate;

    CustomAdapter.OnItemListener onItemListener;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        txtAmount = itemView.findViewById(R.id.txt_amont);
        txtCat = itemView.findViewById(R.id.txt_cat);
        txtDate = itemView.findViewById(R.id.date);
        itemView.setOnClickListener(this);
    }

    public void setOnItemListener(CustomAdapter.OnItemListener itemListener){
        this.onItemListener = itemListener;
    }

    @Override
    public void onClick(View v) {

        this.onItemListener.onItemClick(this.getLayoutPosition());

    }


}
