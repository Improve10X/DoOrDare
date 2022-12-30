package com.improve10x.doordare.pendingfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.improve10x.doordare.Task;
import com.improve10x.doordare.databinding.FragmentPendingBinding;

import java.util.ArrayList;

public class PendingFragment extends Fragment {

    private FragmentPendingBinding binding;
    private ArrayList<Task> tasks = new ArrayList<>();
    private PendingTasksAdapter pendingTasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPendingBinding.inflate(getLayoutInflater());
        setupPendingsAdapter();
        setupPendingTasksRv();
        return binding.getRoot();
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