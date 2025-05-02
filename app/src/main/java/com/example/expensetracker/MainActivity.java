package com.example.expensetracker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.expensetracker.ui.home.ExpenseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.expensetracker.ui.home.ListFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Load default fragment
        loadFragment(new ExpenseFragment());

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            if(itemId == R.id.nav_expense)
            {
                selectedFragment = new ExpenseFragment();
            }
            else if(itemId==R.id.nav_list)
            {
                selectedFragment=new ListFragment();
            }
            if(selectedFragment!=null)
            {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });

        typeSelector = findViewById(R.id.typeSelector);
        radioIncome = findViewById(R.id.radioIncome);
        radioExpense = findViewById(R.id.radioExpense);

        incomeInputLayout = findViewById(R.id.incomeInputLayout);
        expenseInputLayout = findViewById(R.id.expenseInputLayout);

        incomeAmountInput = findViewById(R.id.incomeAmountInput);
        expenseAmountInput = findViewById(R.id.expenseAmountInput);
        expenseDateInput = findViewById(R.id.expenseDateInput);
        categorySpinner = findViewById(R.id.categorySpinner);
        submitButton = findViewById(R.id.submitButton);

        // Set today's date in the date input
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        expenseDateInput.setText(today);

        // Optional: Open date picker on click (can be added later)

        // Handle type selection
        typeSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioIncome) {
                    incomeInputLayout.setVisibility(View.VISIBLE);
                    expenseInputLayout.setVisibility(View.GONE);
                } else if (checkedId == R.id.radioExpense) {
                    incomeInputLayout.setVisibility(View.GONE);
                    expenseInputLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // Handle submission (basic example)
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioIncome.isChecked()) {
                    String income = incomeAmountInput.getText().toString().trim();
                    Toast.makeText(YourActivity.this, "Income added: ₹" + income, Toast.LENGTH_SHORT).show();
                } else if (radioExpense.isChecked()) {
                    String category = categorySpinner.getSelectedItem().toString();
                    String amount = expenseAmountInput.getText().toString().trim();
                    String date = expenseDateInput.getText().toString();
                    Toast.makeText(YourActivity.this, "Expense added: ₹" + amount + " on " + date + " (" + category + ")", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}