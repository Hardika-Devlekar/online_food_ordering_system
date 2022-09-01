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
import com.example.sizzlingbites.ui.user.home.model.ViewModel;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
    Context context;
    ArrayList<ViewModel> viewModels;

    public ViewAdapter(Context context, ArrayList<ViewModel> viewModels) {
        this.context = context;
        this.viewModels = viewModels;
    }

    @NonNull
    @Override
    public ViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Set Holder
        Glide.with(context).load(viewModels.get(position).getImage_url()).into(holder.rowViewImage);
        holder.rowViewName.setText(viewModels.get(position).getName());
        holder.rowViewDesc.setText(viewModels.get(position).getDescription());
        holder.rowViewPrice.setText("₹" + viewModels.get(position).getPrice());
        holder.rowDiscountPrice.setText("₹" + viewModels.get(position).getDiscountPrice());
        holder.rowrating.setText(viewModels.get(position).getRating());

        //strike through on original price of item
        holder.rowViewPrice.setPaintFlags(holder.rowViewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //Set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailedActivity.class);
                intent.putExtra("detailed", viewModels.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewModels.size();// return number of records
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        ImageView rowViewImage;
        TextView rowViewName, rowViewDesc, rowViewPrice, rowrating, rowDiscountPrice, rowdiscountNote;
        CardView rowView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            rowView = itemView.findViewById(R.id.rowView);
            rowViewImage = itemView.findViewById(R.id.rowViewImage);
            rowViewName = itemView.findViewById(R.id.rowViewName);
            rowViewDesc = itemView.findViewById(R.id.rowViewDesc);
            rowViewPrice = itemView.findViewById(R.id.rowViewPrice);
            rowDiscountPrice = itemView.findViewById(R.id.rowDiscountPrice);
            rowdiscountNote = itemView.findViewById(R.id.rowdiscountNote);
            rowrating = itemView.findViewById(R.id.rowrating);
        }
    }
}
