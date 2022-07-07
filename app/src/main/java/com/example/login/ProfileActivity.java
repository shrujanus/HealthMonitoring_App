package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener , SensorEventListener {
    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
  //  private TextView emailText, ageText, usernameText;
    private SensorManager sensorManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolBar;
    MaterialToolbar materialToolbar;
    Sensor mStepCounter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       // logout = findViewById(R.id.logoutButton);
        //logout.setOnClickListener(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        {
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            Toast.makeText(getApplicationContext(), "Wprking", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Not Wprking", Toast.LENGTH_SHORT).show();
        }

  /*      emailText = findViewById(R.id.useremailValue);
        ageText = findViewById(R.id.ageValue);
        usernameText = findViewById(R.id.usernameValue);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null)
                {
                    String fullname = userProfile.fullName;
                    String emailId = userProfile.email;
                    String age = userProfile.age;
                    usernameText.setText(fullname);
                    emailText.setText(emailId);
                    ageText.setText(age);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Some went Wrong!", Toast.LENGTH_SHORT).show();
            }
        });



*/

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        {
            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
            sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }
        materialToolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolBar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolBar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        replaceFragment(new HomeFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        {
            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
            sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
       switch(v.getId())
        {
        //    case R.id.logoutButton:
          //      FirebaseAuth.getInstance().signOut();
           //     startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            //    Toast.makeText(ProfileActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch(item.getItemId())
        {

            case R.id.nav_home:
                materialToolbar.setTitle("Home");
                replaceFragment(new HomeFragment());
                Toast.makeText(this, "Pressed Home", Toast.LENGTH_SHORT).show();
                break;

           /* case R.id.nav_water:
                materialToolbar.setTitle("Water Alarm");
                replaceFragment(new WaterFragment());
                Toast.makeText(this, "Pressed Navigation", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(ProfileActivity.this, water_activity.class));
                break;*/

            case R.id.nav_Steps:
                materialToolbar.setTitle("Step Counter");
                replaceFragment(new StepCounter());
                Toast.makeText(this, "Pressed Foot Step Monitoring", Toast.LENGTH_SHORT).show();
              //  startActivity(new Intent(ProfileActivity.this, StepsActivity.class));
                break;

/*            case R.id.nav_sleepSuggestion:
                materialToolbar.setTitle("Sleep Suggestion");
                replaceFragment(new SleepFragment());
                Toast.makeText(this, "Pressed Sleep Suggestion", Toast.LENGTH_SHORT).show();
              //  startActivity(new Intent(ProfileActivity.this, SleepActivity.class));
                break;*/

            case R.id.nav_dietMonitoring:
                materialToolbar.setTitle("Diet Monitoring");
                replaceFragment(new DietMonitoringFragment());
              //  startActivity(new Intent(ProfileActivity.this, DietActivity.class));
                Toast.makeText(this, "Pressed Diet Monitoring", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_profile:
                materialToolbar.setTitle("Profile");
                replaceFragment(new ProfileInfoFragment());
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                break;

            case R.id.nav_dietSuggestion:
                materialToolbar.setTitle("Diet Suggestion");
                replaceFragment(new DietSuggestionFragment());
                Toast.makeText(this, "Pressed Diet Suggestion", Toast.LENGTH_SHORT).show();
                break;


            default:
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        materialToolbar.setTitle(String.valueOf((int)event.values[0]));

        if(event.sensor == mStepCounter)
        {
            materialToolbar.setTitle(String.valueOf((int)event.values[0]));
            Toast.makeText(getApplicationContext(), String.valueOf((int)event.values[0]), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}