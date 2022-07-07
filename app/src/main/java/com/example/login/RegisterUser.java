package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText FullName, age, Email, TextPassword, height, weight;
    private Button registerUserButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        FullName = findViewById(R.id.fullname);
        age = findViewById(R.id.age);
        Email = findViewById(R.id.emailid);
        TextPassword = findViewById(R.id.password);
        registerUserButton = findViewById(R.id.addDietButton);
        registerUserButton.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        height = findViewById(R.id.Height);
        weight = findViewById(R.id.Weight);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.addDietButton:
                registerUser();

        }
    }


    private void registerUser()
    {
        String email = Email.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();
        String fullName = FullName.getText().toString().trim();
        String Age = age.getText().toString().trim();
        String Height = height.getText().toString().trim();
        String Weight = weight.getText().toString().trim();
        boolean gender;


        if(fullName.isEmpty())
        {
            FullName.setError("Full name is required");
            FullName.requestFocus();
            return;
        }

        if(Age.isEmpty())
        {
            age.setError("Age is required");
            age.requestFocus();
            return;
        }

        if(Height.isEmpty())
        {
            height.setError("Height is required");
            height.requestFocus();
            return;
        }

        if(Weight.isEmpty())
        {
            weight.setError("Weight is required");
            weight.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            TextPassword.setError("Password is required");
            TextPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Email.setError("Please provide valid email!");
            Email.requestFocus();
            return;
        }

        if(password.length() < 6)
        {
            TextPassword.setError("Minimum password length should be 6 characters!");
            TextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user = new User(fullName, Age, email, Weight, Height);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterUser.this, "User has been Registered successfully!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                    }
                                    else{
                                        Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(RegisterUser.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}