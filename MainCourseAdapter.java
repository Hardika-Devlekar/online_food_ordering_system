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
import com.example.sizzlingbites.ui.user.home.model.MainCourseModel;

import java.util.ArrayList;

public class MainCourseAdapter extends RecyclerView.Adapter<MainCourseAdapter.ViewHolder> {
    Context context;
    ArrayList<MainCourseModel> mainCourseModels;

    public MainCourseAdapter(Context context, ArrayList<MainCourseModel> mainCourseModels) {
        this.context = context;
        this.mainCourseModels = mainCourseModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Set Holder
        Glide.with(context).load(mainCourseModels.get(position).getImage_url()).into(holder.mainViewImage);
        holder.mainViewName.setText(mainCourseModels.get(position).getName());
        holder.mainViewDesc.setText(mainCourseModels.get(position).getDesc());
        holder.mainViewPrice.setText("₹" + mainCourseModels.get(position).getPrice());
        holder.mainDiscountPrice.setText("₹" + mainCourseModels.get(position).getDiscountPrice());
        holder.ratingBar3.setText(mainCourseModels.get(position).getRating());

        //strike through on original price of item
        holder.mainViewPrice.setPaintFlags(holder.mainViewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //Set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailedActivity.class);
                intent.putExtra("detailed", mainCourseModels.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainCourseModels.size();// return number of records
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        ImageView mainViewImage;
        TextView mainViewName, mainViewDesc, mainViewPrice, ratingBar3, mainDiscountPrice, maindiscountNote;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            cardView = itemView.findViewById(R.id.cardView4);
            mainViewImage = itemView.findViewById(R.id.mainViewImage);
            mainViewName = itemView.findViewById(R.id.mainViewName);
            mainViewDesc = itemView.findViewById(R.id.mainViewDesc);
            mainViewPrice = itemView.findViewById(R.id.mainViewPrice);
            mainDiscountPrice = itemView.findViewById(R.id.mainDiscountPrice);
            maindiscountNote = itemView.findViewById(R.id.maindiscountNote);
            ratingBar3 = itemView.findViewById(R.id.ratingBar3);
        }
    }
}
