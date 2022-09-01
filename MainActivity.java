package com.example.sizzlingbites.ui.user.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.files.ChangePasswordActivity;
import com.example.sizzlingbites.ui.user.files.LoginActivity;
import com.example.sizzlingbites.ui.user.home.apdater.Adapter;
import com.example.sizzlingbites.ui.user.home.apdater.MainAdapter;
import com.example.sizzlingbites.ui.user.home.apdater.RowAdapter;
import com.example.sizzlingbites.ui.user.home.model.MainModel;
import com.example.sizzlingbites.ui.user.home.model.Model;
import com.example.sizzlingbites.ui.user.home.model.RowModel;
import com.example.sizzlingbites.ui.user.setting.AccountActivity;
import com.example.sizzlingbites.ui.user.setting.CartActivity;
import com.example.sizzlingbites.ui.user.setting.ContactUsActivity;
import com.example.sizzlingbites.ui.user.setting.OfferActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Variables
    ScrollView scrollView;
    ProgressBar progressBar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    //Today's Special Category
    RecyclerView horizontal_recycler_view;
    TextView TodaysSpecial;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;

    //Explore Category
    RecyclerView horizontal_view;
    TextView ExploreCategory;
    ArrayList<RowModel> rowModels;
    RowAdapter rowAdapter;

    //Main Course Category
    TextView mainCourse;
    RecyclerView recyclerView;
    ArrayList<Model> models;
    Adapter adapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();

        //Hooks
        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //Initialization
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseFirestore.getInstance();

        //Today's Special Category
        TodaysSpecial = findViewById(R.id.todaySpecial);
        horizontal_recycler_view = findViewById(R.id.horizontalRecyclerview);
        horizontal_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        mainModels = new ArrayList<>();
        mainAdapter = new MainAdapter(mainModels, getApplicationContext());
        horizontal_recycler_view.setAdapter(mainAdapter);

        rootNode.collection("TodaysSpecial").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        MainModel mainModel = documentSnapshot.toObject(MainModel.class);
                        mainModels.add(mainModel);
                        mainAdapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Explore Category
        ExploreCategory = findViewById(R.id.exploreItems);
        horizontal_view = findViewById(R.id.horizontal_view);
        horizontal_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        rowModels = new ArrayList<>();
        rowAdapter = new RowAdapter(getApplicationContext(), rowModels);
        horizontal_view.setAdapter(rowAdapter);

        rootNode.collection("ExploreCatogeries").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        RowModel rowModel = documentSnapshot.toObject(RowModel.class);
                        rowModels.add(rowModel);
                        mainAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Main Course Category
        mainCourse = findViewById(R.id.mainCourse);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        models = new ArrayList<>();
        adapter = new Adapter(getApplicationContext(), models);
        recyclerView.setAdapter(adapter);

        rootNode.collection("MainCourses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Model model = documentSnapshot.toObject(Model.class);
                        models.add(model);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //SetNavigationView
        navigationView = (NavigationView) findViewById(R.id.navi_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_account:
                        Intent intent1 = new Intent(MainActivity.this, AccountActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_offer:
                        Intent intent2 = new Intent(MainActivity.this, OfferActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_cart:
                        Intent intent3 = new Intent(MainActivity.this, CartActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_changePassword:
                        Intent intent4 = new Intent(MainActivity.this, ChangePasswordActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.nav_contact:
                        Intent intent5 = new Intent(MainActivity.this, ContactUsActivity.class);
                        startActivity(intent5);
                        break;

                    case R.id.nav_logout:
                        Intent intent6 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent6);
                        break;
                }
                return false;
            }
        });
    }

    private void setUpToolbar() {
        //Set DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //Set ActionBar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        actionBarDrawerToggle.syncState();
    }

    //Share app
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*int id = item.getItemId();
        if (id == R.id.nav_share) {
            ApplicationInfo api = getApplicationContext().getApplicationInfo();
            String apkPath = api.sourceDir;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/Vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
            startActivity(Intent.createChooser(intent, "ShareVia"));
        }
        return true;*/

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this application");
        intent.putExtra(Intent.EXTRA_TEXT, "Your application link here");
        startActivity(Intent.createChooser(intent, "ShareVia"));
        return super.onOptionsItemSelected(item);
    }

    //Logout
    public void logout(MenuItem item) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        mAuth.signOut();
        finish();
    }
}