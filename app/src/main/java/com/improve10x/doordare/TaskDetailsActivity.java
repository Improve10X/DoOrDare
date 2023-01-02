package com.improve10x.doordare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.improve10x.doordare.databinding.ActivityTaskDetailsBinding;

import java.util.Date;

import javax.annotation.concurrent.ThreadSafe;

public class TaskDetailsActivity extends AppCompatActivity {

    private Task task;
    private ActivityTaskDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Task Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        task = (Task) getIntent().getSerializableExtra(Constants.KEY_TASK);
        showData();
        handleTaskCompletedBtn();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showData() {
        binding.doTxt.setText(task.doItem.title);
        binding.dareTxt.setText(task.dare.title);
        long deadline = task.doItem.deadlineTimestamp;
        Date date = new Date(deadline);
        String deadlineStr = String.valueOf(date);
        binding.deadlineTxt.setText(deadlineStr);
        binding.statusTxt.setText(task.status);
        if (task.status.equalsIgnoreCase("Pending")) {
            binding.doCompletedBtn.setVisibility(View.VISIBLE);
            binding.materialCardView.setVisibility(View.GONE);
        }
    }

    private void handleTaskCompletedBtn() {
        binding.doCompletedBtn.setOnClickListener(view -> {
            binding.materialCardView.setVisibility(View.VISIBLE);
            binding.doCompletedBtn.setVisibility(View.GONE);
            binding.statusTxt.setText("Do completed");
        });
    }
}