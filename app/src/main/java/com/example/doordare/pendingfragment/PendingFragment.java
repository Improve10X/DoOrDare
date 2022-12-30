package com.example.doordare.pendingfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doordare.databinding.FragmentPendingBinding;

import java.util.ArrayList;

public class PendingFragment extends Fragment {

    private FragmentPendingBinding binding;
    private ArrayList<PendingTask> pendingTasks;
    private PendingTasksAdapter pendingTasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPendingBinding.inflate(getLayoutInflater());
        setupData();
        setupPendingsAdapter();
        setupPendingTasksRv();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void setupData() {
        pendingTasks = new ArrayList<>();
        PendingTask pendingTask = new PendingTask("1", "Do : some Task", "Dare : Some Dare", "10AM", "29", "Dec, 2022", "1 hr left");
        pendingTasks.add(pendingTask);
        pendingTasks.add(pendingTask);
        pendingTasks.add(pendingTask);
        pendingTasks.add(pendingTask);
        PendingTask pendingTask1 = new PendingTask("1", "Do : some Task Task Task Task Task v TaskTaskTaskTaskTaskTask TaskTaskTask ", "Dare : Some Dare Some Dare Some Dare Some Dare Some Dare Some Dare Some Some DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareDare Some DareSome DareSome Dare Some Dare Some Dare", "10AM", "29", "Dec, 2022", "1 hr left");
        pendingTasks.add(pendingTask1);
    }

    private void setupPendingsAdapter() {
        pendingTasksAdapter = new PendingTasksAdapter();
        pendingTasksAdapter.setData(pendingTasks);
    }

    private void setupPendingTasksRv() {
        binding.pendingTasksRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.pendingTasksRv.setAdapter(pendingTasksAdapter);
    }
}