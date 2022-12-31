package com.improve10x.doordare.upcomingfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.doordare.Task;
import com.improve10x.doordare.TaskDetailsActivity;
import com.improve10x.doordare.databinding.FragmentUpcomingBinding;

import java.util.ArrayList;
import java.util.List;

public class UpcomingFragment extends Fragment {

    private ArrayList<Task> tasks = new ArrayList<>();
    private FragmentUpcomingBinding binding;
    private UpcomingTasksAdapter upcomingTasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpcomingBinding.inflate(getLayoutInflater());
        setupUpcomingTasksAdapter();
        setupUpcomingTasksRv();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void setupUpcomingTasksAdapter() {
        upcomingTasksAdapter = new UpcomingTasksAdapter();
        upcomingTasksAdapter.setData(tasks);
        upcomingTasksAdapter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void onItemClicked(Task task) {
                itemClicked();
            }
        });
    }

    private void itemClicked() {
        Intent intent = new Intent(getActivity(), TaskDetailsActivity.class);
        //intent.putExtra("task", task);
        startActivity(intent);
    }

    private void setupUpcomingTasksRv() {
        binding.upcomingTasksRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.upcomingTasksRv.setAdapter(upcomingTasksAdapter);
    }

    private void fetchData() {
        showProgressBar();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            hideProgressBar();
                            List<Task> tasks = task.getResult().toObjects(Task.class);
                            upcomingTasksAdapter.setData(tasks);
                        }
                    }
                });
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }
}