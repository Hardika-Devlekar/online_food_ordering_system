package com.example.sizzlingbites.ui.user.home;

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
import com.example.sizzlingbites.ui.user.home.apdater.MainCourseAdapter;
import com.example.sizzlingbites.ui.user.home.model.MainCourseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainCourseActivity extends AppCompatActivity {
    //init layout views
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ScrollView scrollView;
    ArrayList<MainCourseModel> mainCourseModels;
    MainCourseAdapter mainCourseAdapter;

    private FirebaseFirestore rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_course);
        setUpToolbar();

        //Initialization
        rootNode = FirebaseFirestore.getInstance();

        //Hooks
        progressBar = findViewById(R.id.progressBar1);
        scrollView = findViewById(R.id.scrollView1);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //Create Array List
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.main_course_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mainCourseModels = new ArrayList<>();
        mainCourseAdapter = new MainCourseAdapter(getApplicationContext(), mainCourseModels);
        recyclerView.setAdapter(mainCourseAdapter);

        /////Getting Veg Starter Items
        if (type != null && type.equalsIgnoreCase("veg starter")) {
            rootNode.collection("MainCourseViewAllItems").whereEqualTo("type", "veg starter").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                MainCourseModel mainCourseModel = documentSnapshot.toObject(MainCourseModel.class);
                                mainCourseModels.add(mainCourseModel);
                                mainCourseAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Non Veg Starter Items
        if (type != null && type.equalsIgnoreCase("non veg starter")) {
            rootNode.collection("MainCourseViewAllItems").whereEqualTo("type", "non veg starter").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                MainCourseModel mainCourseModel = documentSnapshot.toObject(MainCourseModel.class);
                                mainCourseModels.add(mainCourseModel);
                                mainCourseAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Veg Soup Items
        if (type != null && type.equalsIgnoreCase("veg soup")) {
            rootNode.collection("MainCourseViewAllItems").whereEqualTo("type", "veg soup").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                MainCourseModel mainCourseModel = documentSnapshot.toObject(MainCourseModel.class);
                                mainCourseModels.add(mainCourseModel);
                                mainCourseAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Non Veg Soup Items
        if (type != null && type.equalsIgnoreCase("non veg soup")) {
            rootNode.collection("MainCourseViewAllItems").whereEqualTo("type", "non veg soup").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                MainCourseModel mainCourseModel = documentSnapshot.toObject(MainCourseModel.class);
                                mainCourseModels.add(mainCourseModel);
                                mainCourseAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Veg Noodles Items
        if (type != null && type.equalsIgnoreCase("veg noodles")) {
            rootNode.collection("MainCourseViewAllItems").whereEqualTo("type", "veg noodles").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                MainCourseModel mainCourseModel = documentSnapshot.toObject(MainCourseModel.class);
                                mainCourseModels.add(mainCourseModel);
                                mainCourseAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Non Veg Noodles Items
        if (type != null && type.equalsIgnoreCase("non veg noodles")) {
            rootNode.collection("MainCourseViewAllItems").whereEqualTo("type", "non veg noodles").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                MainCourseModel mainCourseModel = documentSnapshot.toObject(MainCourseModel.class);
                                mainCourseModels.add(mainCourseModel);
                                mainCourseAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Veg Rice Items
        if (type != null && type.equalsIgnoreCase("veg rice")) {
            rootNode.collection("MainCourseViewAllItems").whereEqualTo("type", "veg rice").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                MainCourseModel mainCourseModel = documentSnapshot.toObject(MainCourseModel.class);
                                mainCourseModels.add(mainCourseModel);
                                mainCourseAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }

        /////Getting Non Veg Rice Items
        if (type != null && type.equalsIgnoreCase("non veg rice")) {
            rootNode.collection("MainCourseViewAllItems").whereEqualTo("type", "non veg rice").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                MainCourseModel mainCourseModel = documentSnapshot.toObject(MainCourseModel.class);
                                mainCourseModels.add(mainCourseModel);
                                mainCourseAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.main_course_toolbar);
        setSupportActionBar(toolbar);
    }
}