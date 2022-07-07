package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register, ForgotPassword;
    private EditText username, password;
    private Button LoginButton;
    private FirebaseAuth mAuth;
    private ProgressBar Progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null)
        {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register);
        username = findViewById(R.id.emailId);
        password = findViewById(R.id.password);
        register.setOnClickListener(this);
        LoginButton = findViewById(R.id.button);
        Progressbar = findViewById(R.id.progressBar);
        ForgotPassword = findViewById(R.id.forgotPassword);
        ForgotPassword.setOnClickListener(this);
        LoginButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                Intent menuIntent = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(menuIntent);
                break;

            case R.id.button:
                Login();
                break;

            case R.id.forgotPassword:
                Intent forgotPasswordIntent = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(forgotPasswordIntent);
                break;
        }
    }

    void Login()
    {
        String Username =  username.getText().toString().trim();
        String Password =  password.getText().toString().trim();
        if(Username.isEmpty())
        {
            username.setError("Email address is required");
            username.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Username).matches())
        {
            username.setError("Please provide valid email!");
            username.requestFocus();
            return;
        }

        if(Password.isEmpty())
        {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(Password.length() < 6)
        {
            password.setError("Minimum Password length is 6 characters !");
            password.requestFocus();
            return;
        }

        Progressbar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(Username, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Progressbar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified())
                    {
                        Intent ProfileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(ProfileIntent);
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "check your email to verify your account", Toast.LENGTH_SHORT).show();
                    }
              }
                else
                {
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}