package com.example.sizzlingbites.ui.user.files;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.home.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    //init layout views
    CheckBox checkbox;
    Button btnSignIn, btn_SignUp;
    TextInputEditText edtName, edtEmail, edtMobile, edtPassword;

    private FirebaseAuth mAuth;
    private FirebaseFirestore rootNode;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //create token for user & admin
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    //Get new Instance ID token
                    token = task.getResult();
                });

        //init ui views
        checkbox = findViewById(R.id.checkBox);
        btnSignIn = findViewById(R.id.btnSignIn);
        btn_SignUp = findViewById(R.id.btnSignUp);
        edtName = findViewById(R.id.txtFullName);
        edtEmail = findViewById(R.id.txtEmailAddress);
        edtMobile = findViewById(R.id.txtMobileNumber);
        edtPassword = findViewById(R.id.txtUserPassword);

        //Initialization
        rootNode = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            //there is some one user logged in
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

        //check box logic
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get all the values
                String userName = edtName.getText().toString().trim();
                String userEmail = edtEmail.getText().toString().trim();
                String userMobile = edtMobile.getText().toString();
                String userPassword = edtPassword.getText().toString();

                if (!validateName() | !validateEmail() | !validateMobile() | !validatePassword()) {
                    return;
                } else {
                    register(userName, userEmail, userMobile, userPassword);
                }
            }
        });

    }

    //start the user registration process
    private void register(String userName, String userEmail, String userMobile, String userPassword) {
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                DocumentReference reference = rootNode.collection("Users").document(mUser.getUid());
                //setup user information
                HashMap<String, String> hashMap = new HashMap<>();
                //specify if the user is admin
                hashMap.put("isUser", "1");
                hashMap.put("userToken", token);
                hashMap.put("userName", userName);
                hashMap.put("userEmail", userEmail);
                hashMap.put("userMobile", userMobile);
                hashMap.put("userPassword", userPassword);
                reference.set(hashMap);

                //Sign up success, update UI with the sign-in user's information
                Toast.makeText(RegisterActivity.this, "Successful... New user account is created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //If sign up fails, display a message to the user
                Toast.makeText(RegisterActivity.this, "Error! Failed to create new user account" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validateName() {
        String val = edtName.getText().toString().trim();
        if (val.isEmpty()) {
            edtName.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 25) {
            edtName.setError("Characters are too long");
            return false;
        } else {
            edtName.setEnabled(false);
            edtName.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = edtEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            edtEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            edtEmail.setError("Invalid email address");
            return false;
        } else {
            edtEmail.setError(null);
            return true;
        }
    }

    private Boolean validateMobile() {
        String val = edtMobile.getText().toString();
        if (val.isEmpty()) {
            edtMobile.setError("Field cannot be empty");
            return false;
        } else if (val.length() > 10) {
            edtMobile.setError("Entered mobile number is too long");
            return false;
        } else {
            edtMobile.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = edtPassword.getText().toString();
        String passwordPattern = "^(?=.*[0-9])" //a digit must occur at least once
                + "(?=.*[a-z])(?=.*[A-Z])" //lower case and upper case alphabet that must occur at least once.
                + "(?=.*[@#$%^&+=])" //a special character that must occur at least once.
                + "(?=\\S+$).{8,}$"; //white spaces don’t allowed , at least 8 characters at the end of the string.
        if (val.isEmpty()) {
            edtPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordPattern)) {
            edtPassword.setError("Password is too weak");
            return false;
        } else {
            edtPassword.setError(null);
            return true;
        }
    }
}