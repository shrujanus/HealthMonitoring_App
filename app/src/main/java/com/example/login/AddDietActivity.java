package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AddDietActivity extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    private AutoCompleteTextView autoCompleteTextView;
    private TextView t1,t2,t3,t4,Cvalue, Pvalue, Fvalue, CatValue, QValue;
    private ImageButton addButton, removeButton;
    private Button addDietButton;

    private String userID, Category;
    private FirebaseUser user;
    private DatabaseReference reference;

    private static final String[] FOODNAMES = new String[]{
            "Tea(With Milk And Sugar)", "Chicken Fried Steak", "Tea"
    };

    private static final String[] Carbohydrates = new String[]{
            "37.8", "27.7", "30"
    };

    private static final String[] FATS = new String[]{
            "6.94", "27.7", "3"
    };

    private static final String[] PROTEINS = new String[]{
            "6.17", "33.4", "3"
    };

    private static final String[] CATEGORY = new String[]{
            "Breakfast", "Meal", "Breakfast"
    };

    void InitialiseViews()
    {
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);

        Cvalue = findViewById(R.id.CValue);
        Pvalue = findViewById(R.id.CValue2);
        Fvalue = findViewById(R.id.CValue3);
        CatValue = findViewById(R.id.CValue4);

        addButton = findViewById(R.id.imageButton);
        removeButton = findViewById(R.id.imageButton2);
        addDietButton = findViewById(R.id.addDietButton);
        QValue = findViewById(R.id.QuantityValue);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QValue.setText(String.valueOf(Integer.parseInt(QValue.getText().toString()) + 1));
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(QValue.getText().toString()) <= 1)
                {
                    return;
                }

                QValue.setText(String.valueOf(Integer.parseInt(QValue.getText().toString()) - 1));

            }
        });

        addDietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoDataBase();
            }
        });
    }

    void AddtoDataBase()
    {
        //foodName, carbohydrates, fats, proteins, category, quantity, createdDate, createdTime
        String foodName = autoCompleteTextView.getText().toString();
        String Carbohydrates = Cvalue.getText().toString();
        String fats = Fvalue.getText().toString();
        String proteins = Pvalue.getText().toString();
        String category = Category;
        String quantity = QValue.getText().toString();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String createdDate = simpleDateFormat.format(new Date(timestamp.getTime()));
        simpleDateFormat = new SimpleDateFormat("h:mm a");
        String createdTime = simpleDateFormat.format(new Date(timestamp.getTime()));

        DietInfo dietInfo = new DietInfo(foodName,Carbohydrates, fats, proteins,
                category, quantity,createdDate, createdTime );

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).child("DietMonitoring").push()
                .setValue(dietInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Diet Added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error while insertion", Toast.LENGTH_SHORT).show();
                                    }
                                }
        );
    }

    void CHANGEVISIBILITY(int visibility)
    {
        t2.setVisibility(visibility);
        t3.setVisibility(visibility);
        t4.setVisibility(visibility);
        Cvalue.setVisibility(visibility);
        Pvalue.setVisibility(visibility);
        Fvalue.setVisibility(visibility);
        CatValue.setVisibility(visibility);
        addButton.setVisibility(visibility);
        removeButton.setVisibility(visibility);
        addDietButton.setVisibility(visibility);
        QValue.setVisibility(visibility);
        QValue.setText("1");

    }


    void DisplayItems(int position)
    {
        Cvalue.setText(Carbohydrates[position]);
        Pvalue.setText(PROTEINS[position]);
        Fvalue.setText(FATS[position]);
        CatValue.setText("Category : " + CATEGORY[position]);
        Category = CATEGORY[position];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);
        materialToolbar = findViewById(R.id.toolbar2);

        InitialiseViews();
        CHANGEVISIBILITY(View.INVISIBLE);

        autoCompleteTextView = findViewById(R.id.foodName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FOODNAMES);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView
                .setOnItemClickListener(new AdapterView.OnItemClickListener(){

                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String searchFetchedWord = adapter.getItem(position);
                                            Toast.makeText(getApplicationContext(), searchFetchedWord, Toast.LENGTH_SHORT).show();
                                            CHANGEVISIBILITY(View.VISIBLE);
                                            DisplayItems(position);
                                        }
                                    });


                        materialToolbar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
    }
}