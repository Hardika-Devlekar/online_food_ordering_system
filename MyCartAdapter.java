package com.example.sizzlingbites.ui.user.home.apdater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.home.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    Context context;
    ArrayList<MyCartModel> myCartModels;
    int totalPrice = 0;

    FirebaseFirestore rootNode;
    FirebaseAuth mAuth;
    String userId;

    public MyCartAdapter(Context context, ArrayList<MyCartModel> myCartModels) {
        this.context = context;
        this.myCartModels = myCartModels;

        rootNode = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Set Holder
        holder.currentDate.setText(myCartModels.get(position).getSaveCurrentDate());
        holder.currentTime.setText(myCartModels.get(position).getSaveCurrentTime());
        holder.itemName.setText(myCartModels.get(position).getItemName());
        holder.itemPrice.setText(myCartModels.get(position).getItemPrice());
        holder.totalQuantity.setText(myCartModels.get(position).getItemQuantity());

        //pass total amount to cart activity
        totalPrice = totalPrice + myCartModels.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        //delete item from cart
        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode.collection("Users").document(userId)
                        .collection("AddToCart").document(myCartModels.get(position).getDocumentId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    myCartModels.remove(myCartModels.get(position));
                                    notifyDataSetChanged();

                                    Toast.makeText(context, "Selected item is deleted from cart...", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error" + task.getResult(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCartModels.size();// return number of records
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //init layout views
        ImageView itemDelete;
        TextView currentDate, currentTime, itemName, itemPrice, totalQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //init ui views
            itemDelete = itemView.findViewById(R.id.item_delete);
            currentDate = itemView.findViewById(R.id.item_date);
            currentTime = itemView.findViewById(R.id.item_time);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.discount);
            totalQuantity = itemView.findViewById(R.id.total_number);
        }
    }
}
