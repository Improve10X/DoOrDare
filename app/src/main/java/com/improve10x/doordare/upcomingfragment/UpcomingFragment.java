package com.improve10x.doordare.upcomingfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.improve10x.doordare.databinding.FragmentUpcomingBinding;

import java.util.ArrayList;

public class UpcomingFragment extends Fragment {

    private ArrayList<UpcomingTask> upcomingTasks;
    private FragmentUpcomingBinding binding;
    private UpcomingTasksAdapter upcomingTasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpcomingBinding.inflate(getLayoutInflater());
        setupData();
        setupUpcomingTasksAdapter();
        setupUpcomingTasksRv();
        return binding.getRoot();
    }

    private void setupData() {
        upcomingTasks = new ArrayList<>();
        UpcomingTask upcomingTask = new UpcomingTask("2", "Do : some task", "Dare : some dare", "10Am", "29", "dec, 2022", "1 day left");
        upcomingTasks.add(upcomingTask);
        upcomingTasks.add(upcomingTask);
        upcomingTasks.add(upcomingTask);
        upcomingTasks.add(upcomingTask);
        upcomingTasks.add(upcomingTask);
    }

    private void setupUpcomingTasksAdapter() {
        upcomingTasksAdapter = new UpcomingTasksAdapter();
        upcomingTasksAdapter.setData(upcomingTasks);
    }

    private void setupUpcomingTasksRv() {
        binding.upcomingTasksRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingTasksRv.setAdapter(upcomingTasksAdapter);
    }
}