package com.example.expensetracker.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ListFragment extends Fragment {

    private final Map<String, Integer> categoryColorMap = new HashMap<String, Integer>() {{
        put("Food", Color.parseColor("#F44336"));       // Red
        put("Transport", Color.parseColor("#2196F3"));  // Blue
        put("Shopping", Color.parseColor("#4CAF50"));   // Green
        put("Entertainment", Color.parseColor("#FF9800")); // Orange
        put("Other", Color.parseColor("#9C27B0"));      // Purple
    }};
    private PieChart pieChart;
    private RecyclerView expenseRecyclerView;
    private ExpenseAdapter adapter;
    private List<ExpenseItem> expenseList = new ArrayList<>();
    private ExpenseViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        pieChart = view.findViewById(R.id.pieChart);
        expenseRecyclerView = view.findViewById(R.id.expenseRecyclerView);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ExpenseAdapter(expenseList);
        expenseRecyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(ExpenseViewModel.class);
        viewModel.getExpenseList().observe(getViewLifecycleOwner(), updatedList -> {
            expenseList.clear();
            expenseList.addAll(updatedList);
            setupPieChart();
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private void setupPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        Map<String, Float> categoryTotals = new HashMap<>();

        for (ExpenseItem item : expenseList) {
            String category = item.getCategory();
            categoryTotals.put(category,
                    categoryTotals.getOrDefault(category, 0f) + item.getAmount());
        }

        for (Map.Entry<String, Float> entry : categoryTotals.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expenses");

        // Assign consistent colors
        List<Integer> colors = new ArrayList<>();
        for (PieEntry entry : entries) {
            String category = entry.getLabel();
            Integer color = categoryColorMap.getOrDefault(category, Color.GRAY); // default if not defined
            colors.add(color);
        }

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(14f);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }
}
