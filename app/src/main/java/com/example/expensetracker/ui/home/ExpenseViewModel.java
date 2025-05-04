package com.example.expensetracker.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseViewModel extends ViewModel {

    private final MutableLiveData<List<ExpenseItem>> expenseList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Float> totalIncome = new MutableLiveData<>(0f);
    private final MutableLiveData<Float> totalExpense = new MutableLiveData<>(0f);

    public LiveData<List<ExpenseItem>> getExpenseList() {
        return expenseList;
    }

    public LiveData<Float> getTotalIncome() {
        return totalIncome;
    }

    public LiveData<Float> getTotalExpense() {
        return totalExpense;
    }

    public void addIncome(float income) {
        Float currentIncome = totalIncome.getValue();
        totalIncome.setValue((currentIncome != null ? currentIncome : 0f) + income);
    }

    public void addExpense(ExpenseItem item) {
        List<ExpenseItem> currentList = expenseList.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        currentList.add(item);
        expenseList.setValue(currentList);

        Float currentExpense = totalExpense.getValue();
        totalExpense.setValue((currentExpense != null ? currentExpense : 0f) + item.getAmount());
    }
}