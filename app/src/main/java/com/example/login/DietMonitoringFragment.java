package com.example.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietMonitoringFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietMonitoringFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button button;
    DietMonitoringAdapter dietMonitoringAdapter;
    private String userID;
    private FirebaseUser user;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private FloatingActionButton fActionButton;
    DatePickerDialog.OnDateSetListener setListener;
    private TextView totalCaloriesintake;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DietMonitoringFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {


        super.onStart();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + String.format("%02d",month) +"-" + String.format("%02d", dayOfMonth);

        SetRecyclerAdapter(date);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DietMonitoringFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DietMonitoringFragment newInstance(String param1, String param2) {
        DietMonitoringFragment fragment = new DietMonitoringFragment();
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

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_diet_monitoring, container, false);
        button = root.findViewById(R.id.FilterButton);
        recyclerView = root.findViewById(R.id.ListOfItems);
        fActionButton = root.findViewById(R.id.floatingActionButton);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        totalCaloriesintake = root.findViewById(R.id.totalCaloriesIntake);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + String.format("%02d",month) +"-" + String.format("%02d", dayOfMonth);

        SetRecyclerAdapter(date);
        button.setText(date);


        fActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDietActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + String.format("%02d",month) +"-" + String.format("%02d", dayOfMonth);
                        button.setText(date);
                        SetRecyclerAdapter(date);
                    }
                }, year, month - 1, dayOfMonth);
                datePickerDialog.show();

            }
        });
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        dietMonitoringAdapter.stopListening();
    }

    void SetRecyclerAdapter(String date)
    {
        Query query = reference.child(userID).child("DietMonitoring")
                .orderByChild("CreatedDate").equalTo(date);

        FirebaseRecyclerOptions<DietInfo> options =
                new FirebaseRecyclerOptions.Builder<DietInfo>()
                        .setQuery(query, DietInfo.class
                        ).build();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float totalCaloriesIntake = 0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    DietInfo dietInfo = postSnapshot.getValue(DietInfo.class);
                    totalCaloriesIntake += Float.parseFloat(dietInfo.TotalCalories);
                }

              //  Toast.makeText(getActivity(), String.valueOf(totalCaloriesIntake), Toast.LENGTH_SHORT).show();
                totalCaloriesintake.setText("Total Intake: " + totalCaloriesIntake + " cal");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        });

        dietMonitoringAdapter  = new DietMonitoringAdapter(options);
        recyclerView.setAdapter(dietMonitoringAdapter);
        dietMonitoringAdapter.startListening();
    }
}