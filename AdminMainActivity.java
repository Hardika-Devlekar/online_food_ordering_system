package com.example.sizzlingbites.ui.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.admin.adapter.PaymentAdapter;
import com.example.sizzlingbites.ui.admin.adapter.StatusAdapter;
import com.example.sizzlingbites.ui.admin.model.PaymentModel;
import com.example.sizzlingbites.ui.admin.model.StatusModel;
import com.example.sizzlingbites.ui.user.files.ChangePasswordActivity;
import com.example.sizzlingbites.ui.user.files.LoginActivity;
import com.example.sizzlingbites.ui.user.setting.AccountActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {
    //init layout views
    DrawerLayout drawerLayout1;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    //Payments Category
    RecyclerView payment_recycler;
    ArrayList<PaymentModel> paymentModels;
    PaymentAdapter paymentAdapter;

    //Orders Category
    RecyclerView order_recycler;
    ArrayList<StatusModel> statusModels;
    StatusAdapter statusAdapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        setUpToolbar();

        //Initialization
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseFirestore.getInstance();

        //Payments Category
        payment_recycler = findViewById(R.id.payment_recycler);
        payment_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        paymentModels = new ArrayList<>();
        paymentAdapter = new PaymentAdapter(getApplicationContext(), paymentModels);
        payment_recycler.setAdapter(paymentAdapter);

        rootNode.collection("Payments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        PaymentModel paymentModel = documentSnapshot.toObject(PaymentModel.class);
                        paymentModels.add(paymentModel);
                        paymentAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Orders Category
        order_recycler = findViewById(R.id.order_recycler);
        order_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        statusModels = new ArrayList<>();
        statusAdapter = new StatusAdapter(getApplicationContext(), statusModels);
        order_recycler.setAdapter(statusAdapter);

        rootNode.collection("Orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        StatusModel statusModel = documentSnapshot.toObject(StatusModel.class);
                        statusModels.add(statusModel);
                        statusAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //SetNavigationView
        navigationView = (NavigationView) findViewById(R.id.navi_menu1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_account1:
                        Intent intent = new Intent(AdminMainActivity.this, AccountActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_changePassword1:
                        Intent intent1 = new Intent(AdminMainActivity.this, ChangePasswordActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_logout1:
                        Intent intent2 = new Intent(AdminMainActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });
    }

    private void setUpToolbar() {
        //Set DrawerLayout
        drawerLayout1 = findViewById(R.id.drawerLayout1);
        Toolbar toolbar = findViewById(R.id.admin_main_toolbar);
        setSupportActionBar(toolbar);

        //Set ActionBar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout1.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();
    }

    //Logout
    public void logout(MenuItem item) {
        startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
        mAuth.signOut();
        finish();
    }
}