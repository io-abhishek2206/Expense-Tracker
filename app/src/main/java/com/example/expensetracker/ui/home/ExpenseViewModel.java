// ExpenseViewModel.java
package com.example.expensetracker.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseViewModel extends ViewModel {
    private final MutableLiveData<List<ExpenseItem>> expenseList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<ExpenseItem>> getExpenseList() {
        return expenseList;
    }

    public void addExpense(ExpenseItem item) {
        List<ExpenseItem> currentList = expenseList.getValue();
        if (currentList != null) {
            currentList.add(item);
            expenseList.setValue(new ArrayList<>(currentList));
        }
    }
}