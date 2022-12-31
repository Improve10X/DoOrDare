package com.improve10x.doordare.pendingfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.doordare.Constants;
import com.improve10x.doordare.HomeActivity;
import com.improve10x.doordare.Task;
import com.improve10x.doordare.TaskDetailsActivity;
import com.improve10x.doordare.databinding.FragmentPendingBinding;

import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends Fragment {

    private FragmentPendingBinding binding;
    private ArrayList<Task> tasks = new ArrayList<>();
    private PendingTasksAdapter pendingTasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPendingBinding.inflate(getLayoutInflater());
        setupPendingTasksAdapter();
        setupPendingTasksRv();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void setupPendingTasksAdapter() {
        pendingTasksAdapter = new PendingTasksAdapter();
        pendingTasksAdapter.setData(tasks);
        pendingTasksAdapter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void onItemClicked(Task task) {
                itemClicked(task);
            }
        });
    }

    private void itemClicked(Task task) {
        Intent intent = new Intent(getActivity(), TaskDetailsActivity.class);
        intent.putExtra(Constants.KEY_TASK, task);
        startActivity(intent);
    }

    private void setupPendingTasksRv() {
        binding.pendingTasksRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.pendingTasksRv.setAdapter(pendingTasksAdapter);
    }

    private void fetchData() {
        showProgressBar();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .orderBy("createdTimestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            hideProgressBar();
                            List<Task> tasks = task.getResult().toObjects(Task.class);
                            pendingTasksAdapter.setData(tasks);
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
