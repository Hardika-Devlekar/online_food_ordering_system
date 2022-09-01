package com.example.sizzlingbites.ui.user.home.apdater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.example.sizzlingbites.ui.user.home.DetailedActivity;
import com.example.sizzlingbites.ui.user.home.model.MainModel;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Context context;
    ArrayList<MainModel> mainModels;

    public MainAdapter(ArrayList<MainModel> mainModels, Context context) {
        this.mainModels = mainModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Set Holder
        Glide.with(context).load(mainModels.get(position).getImage_url()).into(holder.todaySpecialImage);
        holder.todaySpecialName.setText(mainModels.get(position).getName());
        holder.todaySpecialName.setText(mainModels.get(position).getName());
        holder.todaySpecialDesc.setText(mainModels.get(position).getDescription());
        holder.todaySpecialPrice.setText("₹" + mainModels.get(position).getPrice());
        holder.todayDiscountPrice.setText("₹" + mainModels.get(position).getDiscountPrice());
        holder.ratingBar1.setText(mainModels.get(position).getRating());

        //strike through on original price of item
        holder.todaySpecialPrice.setPaintFlags(holder.todaySpecialPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //Set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailedActivity.class);
                intent.putExtra("detailed", mainModels.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainModels.size();// return number of records
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        TextView todaySpecialName, todaySpecialDesc, todaySpecialPrice, ratingBar1, todayDiscountPrice, todaydiscountNote;
        ImageView todaySpecialImage;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            cardView = itemView.findViewById(R.id.cardView1);
            todaySpecialImage = itemView.findViewById(R.id.todaySpecialImage);
            todaySpecialName = itemView.findViewById(R.id.todaySpecialName);
            todaySpecialDesc = itemView.findViewById(R.id.todaySpecialDesc);
            todaySpecialPrice = itemView.findViewById(R.id.todaySpecialPrice);
            todaydiscountNote = itemView.findViewById(R.id.todaydiscountNote);
            todayDiscountPrice = itemView.findViewById(R.id.todayDiscountPrice);
            ratingBar1 = itemView.findViewById(R.id.ratingBar1);
        }
    }
}
