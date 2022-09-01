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
import com.example.sizzlingbites.ui.user.home.MainCourseActivity;
import com.example.sizzlingbites.ui.user.home.model.Model;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Model> models;

    public Adapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(models.get(position).getImage_url()).into(holder.mainCourseImage);
        //Set Holder
        holder.mainCourseName.setText(models.get(position).getName());
        holder.mainCourseDesc.setText(models.get(position).getDescription());
        holder.ratingBar2.setText(models.get(position).getRating());

        //Set OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainCourseActivity.class);
                intent.putExtra("type", models.get(position).getType());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();// return number of records
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        ImageView mainCourseImage;
        TextView mainCourseName, mainCourseDesc, ratingBar2;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            cardView = itemView.findViewById(R.id.cardView3);
            this.mainCourseImage = itemView.findViewById(R.id.mainCourseImage);
            this.mainCourseName = itemView.findViewById(R.id.mainCourseName);
            this.mainCourseDesc = itemView.findViewById(R.id.mainCourseDesc);
            this.ratingBar2 = itemView.findViewById(R.id.ratingBar2);
        }
    }
}
