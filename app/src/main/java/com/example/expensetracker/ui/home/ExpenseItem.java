package com.example.expensetracker.ui.home;

public class ExpenseItem {
    public final String category;
    public final float amount;
    public final String date;

    public ExpenseItem(String category, float amount, String date) {
        this.category = category;
        this.amount = amount;
        this.date = date;

    }
    public String getCategory() {
        return category;
    }

    public float getAmount() {
        return amount;
    }
}