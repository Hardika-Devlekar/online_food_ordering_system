package com.example.sizzlingbites.ui.user.files;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sizzlingbites.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ForgotPasswordActivity extends AppCompatActivity {
    //init layout views
    TextView txtPrefix;
    Button btnReset;
    TextInputEditText edtResetEmail;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //init ui views
        txtPrefix = findViewById(R.id.txt_prefix3);
        btnReset = findViewById(R.id.btnReset);
        edtResetEmail = findViewById(R.id.txtForgotEmailAddress);

        //Initialization
        mAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //start the user reset password process
                mAuth.fetchSignInMethodsForEmail(edtResetEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            txtPrefix.setText("This is not an registered email address, you can create new account");
                        } else {
                            mAuth.sendPasswordResetEmail(edtResetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Sign in success, update UI with the sign-in user's information
                                        txtPrefix.setText("An email to reset password has been sent on your email address");
                                    } else {
                                        //If sign in fails, display a message to the user
                                        txtPrefix.setText(task.getException().getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}