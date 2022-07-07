package com.example.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietSuggestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietSuggestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference reference;
    private String userID;
    private FirebaseUser user;
    TextView BMI, Status;
    RecyclerView recyclerView;
    ArrayList<String> names, carbohydrates, proteins, fats;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DietSuggestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DietSuggestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DietSuggestionFragment newInstance(String param1, String param2) {
        DietSuggestionFragment fragment = new DietSuggestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    void SetStatus(float BMI)
    {
        if(BMI < 18.5)
        {
            Status.setText("UnderWeight");
        }
        else if (BMI < 25)
        {
            Status.setText("Healthy Weight");
        }
        else if(BMI < 30)
        {
            Status.setText("Over Weight");
        }
        else
        {
            Status.setText("Obesity");
        }
    }

    void AddValues()
    {
        names = new ArrayList<>();
        carbohydrates = new ArrayList<>();
        proteins = new ArrayList<>();
        fats = new ArrayList<>();

        names.add("BlackTea");
        names.add("Water");
        names.add("Green Tea");
        names.add("apple Juice");
        names.add("apple");
        names.add("apple Juice");
        names.add("apple");

        carbohydrates.add("0 cal");
        carbohydrates.add("0 cal");
        carbohydrates.add("0 cal");
        carbohydrates.add("0.77 cal");
        carbohydrates.add("2.3 cal");
        carbohydrates.add("0.77 cal");
        carbohydrates.add("2.3 cal");

        proteins.add("3.08 cal");
        proteins.add("0 cal");
        proteins.add("3.08 cal");
        proteins.add("220 cal");
        proteins.add("146.6 cal");
        proteins.add("220 cal");
        proteins.add("146.6 cal");

        fats.add("0 cal");
        fats.add("0 cal");
        fats.add("0 cal");
        fats.add("1.54 cal");
        fats.add("1.54 cal");
        fats.add("1.54 cal");
        fats.add("1.54 cal");
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
            AddValues();

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);
                    if(userProfile != null)
                    {
                        String Weight = userProfile.weight;
                        String Height = userProfile.height;
                        float BMIValue = Float.parseFloat(Weight)/(Float.parseFloat(Height)*Float.parseFloat(Height)) * 10000;
                        String SBMI = String.format(" %.2f",BMIValue);
                        BMI.setText(SBMI);
                        SetStatus(BMIValue);
                        SetRecyclerAdapter();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Some went Wrong!", Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_diet_suggestion, container, false);
        BMI = root.findViewById(R.id.bmiValue);
        Status = root.findViewById(R.id.StatusValue);
        recyclerView = root.findViewById(R.id.RecyclerView);
        return root;
    }


    void SetRecyclerAdapter()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DietSuggestionAdaptor dietSuggestionAdaptor = new DietSuggestionAdaptor(names, carbohydrates, proteins, fats);
        recyclerView.setAdapter(dietSuggestionAdaptor);
    }
}