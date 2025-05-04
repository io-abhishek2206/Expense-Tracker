package com.example.expensetracker.ui.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensetracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpenseFragment extends Fragment {

    private RadioGroup typeSelector;
    private RadioButton radioIncome, radioExpense;
    private LinearLayout incomeInputLayout, expenseInputLayout;
    private EditText incomeAmountInput, expenseAmountInput, expenseDateInput;
    private Spinner categorySpinner;
    private Button submitButton;

    private TextView totalIncomeText;
    private TextView totalExpenseText;

    private ExpenseViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        // Initialize ViewModel (shared with other fragments)
        viewModel = new ViewModelProvider(requireActivity()).get(ExpenseViewModel.class);

        // View bindings
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
        totalIncomeText = view.findViewById(R.id.monthlyIncomeAmount);
        totalExpenseText = view.findViewById(R.id.monthlyExpenseAmount);

        // Set up date picker
        expenseDateInput.setFocusable(false);
        expenseDateInput.setClickable(true);
        expenseDateInput.setOnClickListener(v -> showDatePicker());

        // Set today's date
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        expenseDateInput.setText(today);

        // Toggle input layout based on type
        typeSelector.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioIncome) {
                incomeInputLayout.setVisibility(View.VISIBLE);
                expenseInputLayout.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioExpense) {
                incomeInputLayout.setVisibility(View.GONE);
                expenseInputLayout.setVisibility(View.VISIBLE);
            }
        });

        // Observe LiveData for total values
        viewModel.getTotalIncome().observe(getViewLifecycleOwner(), income ->
                totalIncomeText.setText("₹" + String.format(Locale.getDefault(), "%.2f", income)));

        viewModel.getTotalExpense().observe(getViewLifecycleOwner(), expense ->
                totalExpenseText.setText("₹" + String.format(Locale.getDefault(), "%.2f", expense)));

        // Handle submit button
        submitButton.setOnClickListener(v -> {
            if (radioIncome.isChecked()) {
                String incomeStr = incomeAmountInput.getText().toString().trim();
                if (!incomeStr.isEmpty()) {
                    float income = Float.parseFloat(incomeStr);
                    viewModel.addIncome(income);
                    Toast.makeText(getContext(), "Income added: ₹" + income, Toast.LENGTH_SHORT).show();
                    incomeAmountInput.setText("");
                }
                else {
                    Toast.makeText(getContext(), "Enter Amount", Toast.LENGTH_SHORT).show();
                }
            } else if (radioExpense.isChecked()) {
                String category = categorySpinner.getSelectedItem().toString();
                String amountStr = expenseAmountInput.getText().toString().trim();
                String date = expenseDateInput.getText().toString().trim();

                if (!amountStr.isEmpty()) {
                    float amount = Float.parseFloat(amountStr);
                    ExpenseItem expenseItem = new ExpenseItem(category, amount, date); // includes date
                    viewModel.addExpense(expenseItem);
                    Toast.makeText(getContext(), "Expense added: ₹" + amount + " on " + date + " (" + category + ")", Toast.LENGTH_SHORT).show();
                    expenseAmountInput.setText("");
                }
                else {
                    Toast.makeText(getContext(), "Enter Amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    // ✅ Moved this method OUTSIDE onCreateView
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (datePickerView, year, month, dayOfMonth) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    expenseDateInput.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
}