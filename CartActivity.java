package com.example.sizzlingbites.ui.user.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.activities.AddressActivity;
import com.example.sizzlingbites.ui.user.home.apdater.MyCartAdapter;
import com.example.sizzlingbites.ui.user.home.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    //init layout views
    Button confirmbtn;
    RecyclerView recyclerView;
    ArrayList<MyCartModel> myCartModels;
    MyCartAdapter myCartAdapter;
    TextView totalPrice;

    private FirebaseAuth mAuth;
    private FirebaseFirestore rootNode;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setUpToolbar();

        //Hooks
        totalPrice = findViewById(R.id.totalPrice);
        confirmbtn = findViewById(R.id.confirmbtn);

        //Initialization
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        LocalBroadcastManager.getInstance(getApplication()).registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        //confirm order
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myCartModels = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(getApplicationContext(), myCartModels);
        recyclerView.setAdapter(myCartAdapter);

        rootNode.collection("Users").document(userId)
                .collection("AddToCart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                String documentId = documentSnapshot.getId();
                                MyCartModel myCartModel = documentSnapshot.toObject(MyCartModel.class);
                                myCartModel.setDocumentId(documentId);
                                myCartModels.add(myCartModel);
                                myCartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount", 0);
            totalPrice.setText("₹" + totalBill);
        }
    };
}