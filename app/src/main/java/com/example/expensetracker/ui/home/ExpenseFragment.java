package com.example.expensetracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.example.expensetracker.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExpenseFragment extends Fragment {

    private RadioGroup typeSelector;
    private RadioButton radioIncome, radioExpense;
    private LinearLayout incomeInputLayout, expenseInputLayout;
    private EditText incomeAmountInput, expenseAmountInput, expenseDateInput;
    private Spinner categorySpinner;
    private Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        typeSelector = view.findViewById(R.id.typeSelector);
        radioIncome = view.findViewById(R.id.radioIncome);
        radioExpense = view.findViewById(R.id.radioExpense);

        incomeInputLayout = view.findViewById(R.id.incomeInputLayout);
        expenseInputLayout = view.findViewById(R.id.expenseInputLayout);

        incomeAmountInput = view.findViewById(R.id.incomeAmountInput);
        expenseAmountInput = view.findViewById(R.id.expenseAmountInput);
        expenseDateInput = view.findViewById(R.id.expenseDateInput);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        submitButton = view.findViewById(R.id.submitButton);

        // Set today's date
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        expenseDateInput.setText(today);

        typeSelector.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioIncome) {
                incomeInputLayout.setVisibility(View.VISIBLE);
                expenseInputLayout.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioExpense) {
                incomeInputLayout.setVisibility(View.GONE);
                expenseInputLayout.setVisibility(View.VISIBLE);
            }
        });

        submitButton.setOnClickListener(v -> {
            if (radioIncome.isChecked()) {
                String income = incomeAmountInput.getText().toString().trim();
                Toast.makeText(getContext(), "Income added: ₹" + income, Toast.LENGTH_SHORT).show();
            } else if (radioExpense.isChecked()) {
                String category = categorySpinner.getSelectedItem().toString();
                String amount = expenseAmountInput.getText().toString().trim();
                String date = expenseDateInput.getText().toString();
                Toast.makeText(getContext(), "Expense added: ₹" + amount + " on " + date + " (" + category + ")", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}