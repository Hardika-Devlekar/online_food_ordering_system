package com.example.sizzlingbites.ui.user.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.home.apdater.ViewAdapter;
import com.example.sizzlingbites.ui.user.home.model.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RowActivity extends AppCompatActivity {
    //init layout views
    ProgressBar rowBar;
    RecyclerView recyclerView;
    ScrollView rowView;
    ArrayList<ViewModel> viewModels;
    ViewAdapter viewAdapter;

    private FirebaseFirestore rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row);
        setUpToolbar();

        //Initialization
        rootNode = FirebaseFirestore.getInstance();

        //init ui views
        rowBar = findViewById(R.id.rowBar);
        rowView = findViewById(R.id.rowView);

        rowBar.setVisibility(View.VISIBLE);
        rowView.setVisibility(View.GONE);

        //Create Array List
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        viewModels = new ArrayList<>();
        viewAdapter = new ViewAdapter(getApplicationContext(), viewModels);
        recyclerView.setAdapter(viewAdapter);

        /////Getting Rumali Roll Items
        if (type != null && type.equalsIgnoreCase("rumali roll")) {
            rootNode.collection("ExploreViews").whereEqualTo("type", "rumali roll").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ViewModel viewModel = documentSnapshot.toObject(ViewModel.class);
                                viewModels.add(viewModel);
                                viewAdapter.notifyDataSetChanged();

                                rowBar.setVisibility(View.GONE);
                                rowView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Roti Items
        if (type != null && type.equalsIgnoreCase("roti")) {
            rootNode.collection("ExploreViews").whereEqualTo("type", "roti").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ViewModel viewModel = documentSnapshot.toObject(ViewModel.class);
                                viewModels.add(viewModel);
                                viewAdapter.notifyDataSetChanged();

                                rowBar.setVisibility(View.GONE);
                                rowView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Aloo Items
        if (type != null && type.equalsIgnoreCase("aloo items")) {
            rootNode.collection("ExploreViews").whereEqualTo("type", "aloo items").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ViewModel viewModel = documentSnapshot.toObject(ViewModel.class);
                                viewModels.add(viewModel);
                                viewAdapter.notifyDataSetChanged();

                                rowBar.setVisibility(View.GONE);
                                rowView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Fish Items
        if (type != null && type.equalsIgnoreCase("fish")) {
            rootNode.collection("ExploreViews").whereEqualTo("type", "fish").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ViewModel viewModel = documentSnapshot.toObject(ViewModel.class);
                                viewModels.add(viewModel);
                                viewAdapter.notifyDataSetChanged();

                                rowBar.setVisibility(View.GONE);
                                rowView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Biryani Items
        if (type != null && type.equalsIgnoreCase("biryani")) {
            rootNode.collection("ExploreViews").whereEqualTo("type", "biryani").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ViewModel viewModel = documentSnapshot.toObject(ViewModel.class);
                                viewModels.add(viewModel);
                                viewAdapter.notifyDataSetChanged();

                                rowBar.setVisibility(View.GONE);
                                rowView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Thali Items
        if (type != null && type.equalsIgnoreCase("thali")) {
            rootNode.collection("ExploreViews").whereEqualTo("type", "thali").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ViewModel viewModel = documentSnapshot.toObject(ViewModel.class);
                                viewModels.add(viewModel);
                                viewAdapter.notifyDataSetChanged();

                                rowBar.setVisibility(View.GONE);
                                rowView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Mutton Items
        if (type != null && type.equalsIgnoreCase("mutton")) {
            rootNode.collection("ExploreViews").whereEqualTo("type", "mutton").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ViewModel viewModel = documentSnapshot.toObject(ViewModel.class);
                                viewModels.add(viewModel);
                                viewAdapter.notifyDataSetChanged();

                                rowBar.setVisibility(View.GONE);
                                rowView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Chicken Kabab Items
        if (type != null && type.equalsIgnoreCase("chicken kabab")) {
            rootNode.collection("ExploreViews").whereEqualTo("type", "chicken kabab").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ViewModel viewModel = documentSnapshot.toObject(ViewModel.class);
                                viewModels.add(viewModel);
                                viewAdapter.notifyDataSetChanged();

                                rowBar.setVisibility(View.GONE);
                                rowView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.row_toolbar);
        setSupportActionBar(toolbar);
    }
}