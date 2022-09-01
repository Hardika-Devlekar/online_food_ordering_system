package com.example.sizzlingbites.ui.user.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.FCM.FcmNotificationsSender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {
    //init layout views
    EditText amount, note, name, email, number, upi;
    Button send;

    private FirebaseAuth mAuth;
    private FirebaseFirestore rootNode;

    final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setUpToolbar();

        //Initialization
        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseFirestore.getInstance();

        //init ui views
        amount = findViewById(R.id.pay_amount);
        note = findViewById(R.id.pay_note);
        name = findViewById(R.id.pay_name);
        email = findViewById(R.id.pay_email);
        number = findViewById(R.id.pay_number);
        upi = findViewById(R.id.upi_code);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting all the values
                String sendAmount = amount.getText().toString();
                String sendUPI = upi.getText().toString();
                String sendName = name.getText().toString();
                String sendEmail = email.getText().toString();
                String sendNumber = number.getText().toString();
                String sendNote = note.getText().toString();

                payUPI(sendAmount, sendUPI, sendName, sendEmail, sendNumber, sendNote);
            }
        });
    }

    private void payUPI(String sendAmount, String sendUPI, String sendName, String sendEmail, String sendNumber, String sendNote) {
        FirebaseUser mUser = mAuth.getCurrentUser();
        DocumentReference reference = rootNode.collection("Payments").document(mUser.getUid());
        //setup user payment information
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userAmount", sendAmount);
        hashMap.put("userUPI", sendUPI);
        hashMap.put("userName", sendName);
        hashMap.put("userEmail", sendEmail);
        hashMap.put("userNumber", sendNumber);
        hashMap.put("userNote", sendNote);

        reference.set(hashMap);

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", sendUPI)
                .appendQueryParameter("am", sendAmount)
                .appendQueryParameter("pn", sendName)
                .appendQueryParameter("pe", sendEmail)
                .appendQueryParameter("pm", sendNumber)
                .appendQueryParameter("tn", sendNote)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        //will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        //check if intent resolves
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(PaymentActivity.this, "No UPI app found, please install one to continue...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String text = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult:" + text);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(text);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult:" + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult:" + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PaymentActivity.this)) {
            String string = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation:" + string);
            String paymentCancel = "";
            if (string == null) string = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = string.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalString[] = response[i].split("=");
                if (equalString.length >= 2) {
                    if (equalString[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalString[1].toLowerCase();
                    } else if (equalString[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalString[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalString[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(PaymentActivity.this, "Transaction successful...", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaymentActivity.this, "Payment cancelled by user!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PaymentActivity.this, "Transaction failed!! Please try again...", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PaymentActivity.this, "Internet connection is not available!! Please check and try again...", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
    }
}