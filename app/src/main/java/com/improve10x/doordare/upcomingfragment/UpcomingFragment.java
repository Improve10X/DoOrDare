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
import com.improve10x.doordare.AddTaskActivity;
import com.improve10x.doordare.base.Constants;
import com.improve10x.doordare.base.OnItemActionListener;
import com.improve10x.doordare.base.task.Task;
import com.improve10x.doordare.TaskDetailsActivity;
import com.improve10x.doordare.databinding.FragmentUpcomingBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        handleFab();
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
                itemClicked(task);
            }
        });
    }

    private void itemClicked(Task task) {
        Intent intent = new Intent(getActivity(), TaskDetailsActivity.class);
        intent.putExtra(Constants.KEY_TASK, task);
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
                .whereGreaterThan("doItem.deadlineTimestamp", upcomingDateInMillis())
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

    private void handleFab() {
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddTaskActivity.class);
            startActivity(intent);
        });
    }

    //find current date
    //convert current date into dd MM yyyy
    //convert dd MM yyyy to date obj
    //get millis from date obj
    //Add 24hrs equivalent milli seconds

    private long upcomingDateInMillis() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        String currentDateText = dateFormat.format(currentDate);
        Date date = null;
        try {
            date = dateFormat.parse(currentDateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        millis = millis + 24 * 60 * 60 * 1000;
        return millis;
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }
}