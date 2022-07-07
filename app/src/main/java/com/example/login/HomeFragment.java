package com.example.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView  neededCalories, caloriesConsumed;
    private DatabaseReference reference;
    private String userID;
    private FirebaseUser user;
    int CaloriesNeeded;
    private CircularProgressBar circularProgressBar;
    float totalCaloriesIntake = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);;
        neededCalories = root.findViewById(R.id.NeededCalories);
        caloriesConsumed = root.findViewById(R.id.tv_caloriesConsumed);
        circularProgressBar = root.findViewById(R.id.progress_circular2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String Weight = userProfile.weight;
                    String Height = userProfile.height;
                    float BMIValue = Float.parseFloat(Weight) / (Float.parseFloat(Height) * Float.parseFloat(Height)) * 10000;
                    String SBMI = String.format(" %.2f", BMIValue);
                    SetCaloriesNeeded(BMIValue);

                    caloriesConsumed.setText("Consumed:  "+ String.format("%.2f",totalCaloriesIntake) + " cal");
                    neededCalories.setText("Needed: " + String.format("%.2f",CaloriesNeeded - totalCaloriesIntake) + " cal");
                    circularProgressBar.setProgressWithAnimation(totalCaloriesIntake, (long)2000);
                    circularProgressBar.setProgressMax(CaloriesNeeded);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + String.format("%02d",month) +"-" + String.format("%02d", dayOfMonth);

        Query query = reference.child(userID).child("DietMonitoring")
                .orderByChild("CreatedDate").equalTo(date);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    DietInfo dietInfo = postSnapshot.getValue(DietInfo.class);
                    totalCaloriesIntake += Float.parseFloat(dietInfo.TotalCalories);
                }

                //  Toast.makeText(getActivity(), String.valueOf(totalCaloriesIntake), Toast.LENGTH_SHORT).show();
                caloriesConsumed.setText("Consumed:  "+ String.valueOf(totalCaloriesIntake) + " cal");
                neededCalories.setText("Needed: " + String.valueOf(CaloriesNeeded - totalCaloriesIntake) + " cal");
                circularProgressBar.setProgressWithAnimation(totalCaloriesIntake, (long)2000);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });

        return root;
    }


    void SetCaloriesNeeded(float BMI)
    {
        if(BMI < 18.5)
        {
            CaloriesNeeded = 2400;
        }
        else if (BMI < 25)
        {
            CaloriesNeeded = 2000;
        }
        else if(BMI < 30)
        {
            CaloriesNeeded = 1800;
        }
        else
        {
            CaloriesNeeded = 1400;
        }
    }
}