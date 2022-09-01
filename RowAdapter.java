package com.example.sizzlingbites.ui.user.home.apdater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.activities.RowActivity;
import com.example.sizzlingbites.ui.user.home.model.RowModel;

import java.util.ArrayList;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ViewHolder> {
    Context context;
    ArrayList<RowModel> rowModels;

    public RowAdapter(Context context, ArrayList<RowModel> rowModels) {
        this.context = context;
        this.rowModels = rowModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(rowModels.get(position).getImage_url()).into(holder.exploreItemsImage);

        //Set holder
        holder.exploreItemsName.setText(rowModels.get(position).getName());

        //Set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RowActivity.class);
                intent.putExtra("type", rowModels.get(position).getType());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowModels.size();// return number of records
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        ImageView exploreItemsImage;
        TextView exploreItemsName;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            cardView = itemView.findViewById(R.id.cardView2);
            exploreItemsImage = itemView.findViewById(R.id.exploreItemsImage);
            exploreItemsName = itemView.findViewById(R.id.exploreItemsName);
        }
    }
}
