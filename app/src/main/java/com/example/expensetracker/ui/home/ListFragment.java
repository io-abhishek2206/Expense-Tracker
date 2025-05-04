package com.example.expensetracker.ui.home;

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
import java.util.List;

public class ListFragment extends Fragment {

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
        for (ExpenseItem item : expenseList) {
            entries.add(new PieEntry(item.getAmount(), item.getCategory()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expenses");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(14f);
        pieChart.setData(data);
        pieChart.invalidate();
    }
}
