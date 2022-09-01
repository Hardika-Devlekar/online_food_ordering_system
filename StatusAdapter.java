package com.example.sizzlingbites.ui.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.admin.model.StatusModel;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    Context context;
    ArrayList<StatusModel> statusModels;

    public StatusAdapter(Context context, ArrayList<StatusModel> statusModels) {
        this.context = context;
        this.statusModels = statusModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Set holder
        holder.order_date.setText(statusModels.get(position).getOrderDate());
        holder.order_time.setText(statusModels.get(position).getOrderTime());
        holder.order_name.setText(statusModels.get(position).getOrderName());
        holder.order_quantity.setText(statusModels.get(position).getOrderQuantity());
        holder.order_status.setText(statusModels.get(position).getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return statusModels.size();// return number of records
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        CardView statusCard;
        TextView order_date, order_time, order_name, order_quantity, order_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            order_date = itemView.findViewById(R.id.order_date);
            order_time = itemView.findViewById(R.id.order_time);
            order_name = itemView.findViewById(R.id.order_name);
            order_quantity = itemView.findViewById(R.id.order_quantity);
            order_status = itemView.findViewById(R.id.order_status);
            statusCard = itemView.findViewById(R.id.statusCard);
        }
    }
}
