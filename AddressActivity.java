package com.example.sizzlingbites.ui.user.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.home.apdater.AddressAdapter;
import com.example.sizzlingbites.ui.user.home.model.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    //init layout views
    Button addAddressbtn, payNowbtn;
    RecyclerView recyclerView;
    private ArrayList<AddressModel> addressModels;
    private AddressAdapter addressAdapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore rootNode;
    String userId;

    String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setUpToolbar();

        //Initialization
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        //init ui views
        addAddressbtn = findViewById(R.id.add_new_address);
        payNowbtn = findViewById(R.id.payment);

        payNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        addAddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.address_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModels = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(), addressModels, this);
        recyclerView.setAdapter(addressAdapter);

        //get data
        rootNode.collection("Addresses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        AddressModel addressModel = documentSnapshot.toObject(AddressModel.class);
                        addressModels.add(addressModel);
                        addressAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}