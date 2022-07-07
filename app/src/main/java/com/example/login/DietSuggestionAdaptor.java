package com.example.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DietSuggestionAdaptor extends RecyclerView.Adapter {

    ArrayList<String> names, carbohydrates, protiens, fats;

    public DietSuggestionAdaptor(ArrayList<String> names, ArrayList<String> carbohydrates, ArrayList<String> protiens, ArrayList<String> fats) {
        this.names = names;
        this.carbohydrates = carbohydrates;
        this.protiens = protiens;
        this.fats = fats;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diet_suggestion_items, parent, false);
        ViewHolder v = new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tname.setText(names.get(position));
        viewHolder.tcarbohydates.setText(carbohydrates.get(position));
        viewHolder.tproteins.setText(protiens.get(position));
        viewHolder.tfats.setText(fats.get(position));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tname, tcarbohydates, tfats, tproteins;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tname = itemView.findViewById(R.id.FoodItem);
            tcarbohydates = itemView.findViewById(R.id.CarbohydratesValue);
            tfats = itemView.findViewById(R.id.FatsValue);
            tproteins = itemView.findViewById(R.id.ProteinsValue);
        }
    }
}
