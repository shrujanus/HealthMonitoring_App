package com.example.login;

public class DietInfo {
    public String FoodName, Carbohydrates, fats, proteins, Category, Quantity, CreatedDate, CreatedTime, TotalCalories;
    public DietInfo() {
    }

    public DietInfo(String foodName, String carbohydrates, String fats, String proteins, String category, String quantity, String createdDate, String createdTime) {
        FoodName = foodName;
        Carbohydrates = carbohydrates;
        this.fats = fats;
        this.proteins = proteins;
        Category = category;
        Quantity = quantity;
        CreatedDate = createdDate;
        CreatedTime = createdTime;
        TotalCalories =  String.format("%.2f",(Float.parseFloat(Carbohydrates) + Float.parseFloat(fats) + Float.parseFloat(proteins))* Float.parseFloat(Quantity)) ;
    }
}
