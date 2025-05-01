package com.example.expensetracker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}