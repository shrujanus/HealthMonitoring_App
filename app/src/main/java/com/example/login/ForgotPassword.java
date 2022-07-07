package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {
    private EditText emailAddress;
    private Button resetButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailAddress = findViewById(R.id.editTextTextEmailAddress);
        resetButton = findViewById(R.id.PasswordresetButton);
        mAuth = FirebaseAuth.getInstance();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    void resetPassword()
    {
        String email = emailAddress.getText().toString().trim();
        if(email.isEmpty())
        {
            emailAddress.setError("Enter Email Address");
            emailAddress.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailAddress.setError("Enter Valid mail!");
            emailAddress.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Password Reset link sent!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ForgotPassword.this, "Something wrong happened! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}