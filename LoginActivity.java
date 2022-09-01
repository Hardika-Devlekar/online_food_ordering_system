package com.example.sizzlingbites.ui.user.files;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.admin.activities.AdminMainActivity;
import com.example.sizzlingbites.ui.user.home.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    //init layout views
    TextInputEditText edtUserEmail, edtUserPassword;
    Button btnSignIn, btn_SignUp;
    TextView txtForgotPassword;

    private FirebaseAuth mAuth;
    private FirebaseFirestore rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init ui views
        btn_SignUp = findViewById(R.id.SignUp_btn);
        btnSignIn = findViewById(R.id.SignIn_btn);
        edtUserEmail = findViewById(R.id.txtUserEmail);
        edtUserPassword = findViewById(R.id.txtUserPassword);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);

        //Initialization
        rootNode = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            //there is some one user logged in
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get all the values
                String userEmail = edtUserEmail.getText().toString().trim();
                String userPassword = edtUserPassword.getText().toString();

                if (!validateEmail() | !validatePassword()) {
                    return;
                } else {
                    login(userEmail, userPassword);
                }
            }
        });
    }

    private void login(String userEmail, String userPassword) {
        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                checkUserAccessLevel(authResult.getUser().getUid());
                Toast.makeText(LoginActivity.this, "Logged in successfully...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //If sign up fails, display a message to the user
                Toast.makeText(LoginActivity.this, "Error! Failed to log in" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference reference = rootNode.collection("Users").document(uid);
        //extract data from the firestore database document
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //Sign in success, update UI with the sign-in user's information
                Log.d("TAG", "onSuccess:" + documentSnapshot.getData());

                //identify the user access level
                if (documentSnapshot.getString("isAdmin") != null) {
                    //user is admin
                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                    finish();
                }
                if (documentSnapshot.getString("isUser") != null) {
                    //user is user
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private Boolean validateEmail() {
        String val = edtUserEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            edtUserEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            edtUserEmail.setError("Invalid email address");
            return false;
        } else {
            edtUserEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = edtUserPassword.getText().toString();
        String passwordPattern = "^(?=.*[0-9])" //a digit must occur at least once
                + "(?=.*[a-z])(?=.*[A-Z])" //lower case and upper case alphabet that must occur at least once.
                + "(?=.*[@#$%^&+=])" //a special character that must occur at least once.
                + "(?=\\S+$).{8,}$"; //white spaces don’t allowed , at least 8 characters at the end of the string.
        if (val.isEmpty()) {
            edtUserPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordPattern)) {
            edtUserPassword.setError("Password is too weak");
            return false;
        } else {
            edtUserPassword.setError(null);
            return true;
        }
    }
}