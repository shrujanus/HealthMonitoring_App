package com.example.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class DietMonitoringAdapter extends FirebaseRecyclerAdapter<DietInfo, DietMonitoringAdapter.myViewHolder>
{

    public DietMonitoringAdapter(@NonNull FirebaseRecyclerOptions<DietInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DietMonitoringAdapter.myViewHolder holder, int position, @NonNull DietInfo model) {
        holder.tFoodName.setText(model.FoodName);
        holder.tCalories.setText(model.TotalCalories + " cal");
        holder.tCreatedTime.setText(String.valueOf(model.CreatedTime));
        holder.tQuantity.setText("Quantity : " + model.Quantity);
    }

    @NonNull
    @Override
    public DietMonitoringAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_monitoring_items, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView tFoodName, tCarbohydrates, tCalories, tQuantity, tCreatedDate, tCreatedTime;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tFoodName = itemView.findViewById(R.id.FoodItemName);
            tCreatedTime = itemView.findViewById(R.id.time);
            tQuantity = itemView.findViewById(R.id.QuantityValue);
            tCalories = itemView.findViewById(R.id.totalCaloriesValue);
        }
    }
}
