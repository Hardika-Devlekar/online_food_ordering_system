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
import com.example.sizzlingbites.ui.user.home.model.OfferModel;

import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    Context context;
    ArrayList<OfferModel> offerModels;

    public OfferAdapter(Context context, ArrayList<OfferModel> offerModels) {
        this.context = context;
        this.offerModels = offerModels;
    }

    @NonNull
    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer, parent, false);
        return new OfferAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Set Holder
        Glide.with(context).load(offerModels.get(position).getImage_url()).into(holder.offerViewImage);
        holder.offerViewName.setText(offerModels.get(position).getName());
        holder.offerViewDesc.setText(offerModels.get(position).getDesc());
        holder.offerViewPrice.setText("₹" + offerModels.get(position).getPrice());
        holder.offerDiscountPrice.setText("₹" + offerModels.get(position).getDiscountPrice());
        holder.offerRating.setText(offerModels.get(position).getRating());

        //strike through on original price of item
        holder.offerViewPrice.setPaintFlags(holder.offerViewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //Set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailedActivity.class);
                intent.putExtra("detailed", offerModels.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        ImageView offerViewImage;
        TextView offerViewName, offerViewDesc, offerViewPrice, offerRating, offerDiscountPrice, offerDiscountNote;
        CardView offerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            offerView = itemView.findViewById(R.id.offerView);
            offerViewImage = itemView.findViewById(R.id.offerViewImage);
            offerViewName = itemView.findViewById(R.id.offerViewName);
            offerViewDesc = itemView.findViewById(R.id.offerViewDesc);
            offerViewPrice = itemView.findViewById(R.id.offerViewPrice);
            offerDiscountPrice = itemView.findViewById(R.id.offerDiscountPrice);
            offerDiscountNote = itemView.findViewById(R.id.offerDiscountNote);
            offerRating = itemView.findViewById(R.id.offerRating);
        }
    }
}
