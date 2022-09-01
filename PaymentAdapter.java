package com.example.sizzlingbites.ui.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.admin.model.PaymentModel;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    Context context;
    ArrayList<PaymentModel> paymentModels;

    public PaymentAdapter(Context context, ArrayList<PaymentModel> paymentModels) {
        this.context = context;
        this.paymentModels = paymentModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Set holder
        holder.user_name.setText(paymentModels.get(position).getUserName());
        holder.user_email.setText(paymentModels.get(position).getUserEmail());
        holder.user_number.setText(paymentModels.get(position).getUserNumber());
        holder.user_amount.setText("₹" + paymentModels.get(position).getUserAmount());
        holder.user_upi.setText(paymentModels.get(position).getUserUPI());
        holder.user_note.setText(paymentModels.get(position).getUserNote());
    }

    @Override
    public int getItemCount() {
        return paymentModels.size();// return number of records
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        TextView user_name, user_email, user_number, user_amount, user_upi, user_note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            user_name = itemView.findViewById(R.id.user_name);
            user_email = itemView.findViewById(R.id.user_email);
            user_number = itemView.findViewById(R.id.user_number);
            user_amount = itemView.findViewById(R.id.user_amount);
            user_upi = itemView.findViewById(R.id.user_upi);
            user_note = itemView.findViewById(R.id.user_note);
        }
    }
}
