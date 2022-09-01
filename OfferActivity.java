package com.example.sizzlingbites.ui.user.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.home.apdater.OfferAdapter;
import com.example.sizzlingbites.ui.user.home.model.OfferModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OfferActivity extends AppCompatActivity {
    //init layout views
    ProgressBar offerBar;
    RecyclerView recyclerView;
    ArrayList<OfferModel> offerModels;
    OfferAdapter offerAdapter;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        setUpToolbar();


        //Initialization
        db = FirebaseFirestore.getInstance();

        //Hooks
        offerBar = findViewById(R.id.offerBar);

        offerBar.setVisibility(View.VISIBLE);

        //Create Array List
        recyclerView = findViewById(R.id.offer_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        offerModels = new ArrayList<>();
        offerAdapter = new OfferAdapter(getApplicationContext(), offerModels);
        recyclerView.setAdapter(offerAdapter);

        db.collection("Offers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        OfferModel offerModel = documentSnapshot.toObject(OfferModel.class);
                        offerModels.add(offerModel);
                        offerAdapter.notifyDataSetChanged();

                        offerBar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.offer_toolbar);
        setSupportActionBar(toolbar);
    }
}