package com.example.sizzlingbites.ui.user.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sizzlingbites.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    //init layout views
    EditText ad_Address1, ad_Address2, ad_State, ad_City, ad_Code;
    Button add_address;

    private FirebaseAuth mAuth;
    private FirebaseFirestore rootNode;
    String userId;

    String final_address = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        setUpToolbar();

        //init ui views
        ad_Address1 = findViewById(R.id.ad_Address1);
        ad_Address2 = findViewById(R.id.ad_Address2);
        ad_State = findViewById(R.id.ad_State);
        ad_City = findViewById(R.id.ad_City);
        ad_Code = findViewById(R.id.ad_Code);
        add_address = findViewById(R.id.add_address_btn);

        //Initialization
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        //set data
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userAddress1 = ad_Address1.getText().toString();
                String userAddress2 = ad_Address2.getText().toString();
                String userState = ad_State.getText().toString();
                String userCity = ad_City.getText().toString();
                String userCode = ad_Code.getText().toString();

                if (!userAddress1.isEmpty()) {
                    final_address += userAddress1;
                }
                if (!userAddress2.isEmpty()) {
                    final_address += userAddress2;
                }
                if (!userState.isEmpty()) {
                    final_address += userState;
                }
                if (!userCity.isEmpty()) {
                    final_address += userCity;
                }
                if (!userCode.isEmpty()) {
                    final_address += userCode;
                }
                if (!userAddress1.isEmpty() && !userAddress2.isEmpty() && !userState.isEmpty() && !userCity.isEmpty() && !userCode.isEmpty()) {
                    DocumentReference reference = rootNode.collection("Addresses").document(userId);
                    //setup user address
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("userAddress", final_address);
                    reference.set(stringMap);

                    Toast.makeText(AddAddressActivity.this, "Successful... New address is added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
    }
}