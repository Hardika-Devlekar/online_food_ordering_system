package com.example.sizzlingbites.ui.user.files;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sizzlingbites.R;
import com.example.sizzlingbites.ui.user.home.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    //init layout views
    TextInputEditText edtOldPassword, edtNewPassword, edtRePassword;
    Button btnChangePassword;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setUpToolbar();

        //init ui views
        edtOldPassword = findViewById(R.id.txtUserOldchangePassword);
        edtNewPassword = findViewById(R.id.txtUserNewchangePassword);
        edtRePassword = findViewById(R.id.txtUserRechangePassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        //Initialization
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get all values
                String txtOldPassword = edtOldPassword.getText().toString();
                String txtNewPassword = edtNewPassword.getText().toString();
                String txtRePassword = edtRePassword.getText().toString();

                if (!validateOldPassword() | !validateNewPassword() | !validateRePassword()) {
                    return;
                } else {
                    changePassword(txtOldPassword, txtNewPassword, txtRePassword);
                }
            }
        });
    }

    private boolean validateRePassword() {
        String val = edtRePassword.getText().toString();
        if (val.isEmpty()) {
            edtRePassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(String.valueOf(edtNewPassword))) {
            edtRePassword.setError("Password does not matches to new password");
            return false;
        } else {
            edtRePassword.setError(null);
            return true;
        }
    }

    private boolean validateNewPassword() {
        String val = edtNewPassword.getText().toString();
        String passwordPattern = "^(?=.*[0-9])" //a digit must occur at least once
                + "(?=.*[a-z])(?=.*[A-Z])" //lower case and upper case alphabet that must occur at least once.
                + "(?=.*[@#$%^&+=])" //a special character that must occur at least once.
                + "(?=\\S+$).{8,}$"; //white spaces don’t allowed , at least 8 characters at the end of the string.
        if (val.isEmpty()) {
            edtNewPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordPattern)) {
            edtNewPassword.setError("Password is too weak");
            return false;
        } else {
            edtNewPassword.setError(null);
            return true;
        }
    }

    private boolean validateOldPassword() {
        String val = edtOldPassword.getText().toString();
        String passwordPattern = "^(?=.*[0-9])" //a digit must occur at least once
                + "(?=.*[a-z])(?=.*[A-Z])" //lower case and upper case alphabet that must occur at least once.
                + "(?=.*[@#$%^&+=])" //a special character that must occur at least once.
                + "(?=\\S+$).{8,}$"; //white spaces don’t allowed , at least 8 characters at the end of the string.
        if (val.isEmpty()) {
            edtOldPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordPattern)) {
            edtOldPassword.setError("Password is too weak");
            return false;
        } else {
            edtOldPassword.setError(null);
            return true;
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.password_toolbar);
        setSupportActionBar(toolbar);
    }

    //start the user & admin change password process
    private void changePassword(String txtOldPassword, String txtNewPassword, String txtRePassword) {
        AuthCredential credential = EmailAuthProvider.getCredential(mUser.getEmail(), txtOldPassword);
        mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mUser.updatePassword(txtNewPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mAuth.signOut();
                                Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}