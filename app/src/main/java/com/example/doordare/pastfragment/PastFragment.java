package com.example.doordare.pastfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doordare.databinding.FragmentPastBinding;

import java.util.ArrayList;

public class PastFragment extends Fragment {

    private FragmentPastBinding binding;
    private ArrayList<PastTask> pastTasks;
    private PastTasksAdapter pastTasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPastBinding.inflate(getLayoutInflater());
        setData();
        setupPastTasksAdapter();
        setupPastTasksRv();
        return binding.getRoot();
    }

    private void setData() {
        pastTasks = new ArrayList<>();
        PastTask pastTask = new PastTask("2", "Do : some task", "Dare : some dare", "10Am", "29", "dec, 2022", "1 day left");
        pastTasks.add(pastTask);
        pastTasks.add(pastTask);
        pastTasks.add(pastTask);
        pastTasks.add(pastTask);
        pastTasks.add(pastTask);
    }

    private void setupPastTasksAdapter() {
        pastTasksAdapter = new PastTasksAdapter();
        pastTasksAdapter.setData(pastTasks);
    }

    private void setupPastTasksRv() {
        binding.pastTasksRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.pastTasksRv.setAdapter(pastTasksAdapter);
    }
}