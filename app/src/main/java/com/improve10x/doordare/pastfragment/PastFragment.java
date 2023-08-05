package com.improve10x.doordare.pastfragment;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.doordare.base.Constants;
import com.improve10x.doordare.base.OnItemActionListener;
import com.improve10x.doordare.base.task.Task;
import com.improve10x.doordare.TaskDetailsActivity;
import com.improve10x.doordare.databinding.FragmentPastBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PastFragment extends Fragment {

    private FragmentPastBinding binding;
    private ArrayList<Task> tasks = new ArrayList<>();
    private PastTasksAdapter pastTasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPastBinding.inflate(getLayoutInflater());
        setupPastTasksAdapter();
        setupPastTasksRv();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void setupPastTasksAdapter() {
        pastTasksAdapter = new PastTasksAdapter();
        pastTasksAdapter.setData(tasks);
    }

    private void setupPastTasksRv() {
        binding.pastTasksRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.pastTasksRv.setAdapter(pastTasksAdapter);
        pastTasksAdapter.setOnItemActionListener(new OnItemActionListener() {
            @Override
            public void onItemClicked(Task task) {
                itemClicked(task);
            }
        });
        pastTasksAdapter.setOnDeleteActionListener(taskId -> {
            deleteTask(taskId);
        });

    }

    private void itemClicked(Task task) {
        Intent intent = new Intent(getActivity(), TaskDetailsActivity.class);
        intent.putExtra(Constants.KEY_TASK, task);
        startActivity(intent);
    }

    private void fetchData() {
        showProgressBar();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/tasks")
                .whereIn("status", Arrays.asList("Do Completed", "Dare Completed"))
                .orderBy("createdTimestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            hideProgressBar();
                            List<Task> tasks = task.getResult().toObjects(Task.class);
                            if (tasks.isEmpty() == false) {
                                pastTasksAdapter.setData(tasks);
                                dataScreen();
                            } else {
                                emptyScreen();
                            }
                        }
                    }
                });
    }

    private void showProgressBar() {
        binding.progress.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progress.setVisibility(View.GONE);
    }

    private void emptyScreen() {
        binding.pastScreenInfoTxt.setVisibility(View.VISIBLE);
        binding.pastTasksRv.setVisibility(View.GONE);
    }

    private void dataScreen() {
        binding.pastScreenInfoTxt.setVisibility(View.GONE);
        binding.pastTasksRv.setVisibility(View.VISIBLE);
    }

    private void deleteTask(String taskId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/tasks")
                .document(taskId)
                .delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                });
    }
}