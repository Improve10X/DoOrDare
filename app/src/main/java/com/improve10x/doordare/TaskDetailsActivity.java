package com.improve10x.doordare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.doordare.databinding.ActivityTaskDetailsBinding;
import com.squareup.picasso.Picasso;

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
            pending();
        } else if (task.status.equalsIgnoreCase("Do Completed")) {
            doCompleted();
        }
    }

    private void pending() {
        binding.doCompletedBtn.setVisibility(View.VISIBLE);
        binding.materialCardView.setVisibility(View.GONE);
        handleTaskCompletedBtn();
    }

    private void doCompleted() {
        binding.materialCardView.setVisibility(View.VISIBLE);
        binding.statusTxt.setText("Do Completed");
        Picasso.get().load("https://lh4.googleusercontent.com/proxy/rwcYSyNHPdrUh70BGjqH9bNpQzMphBVK52yd8xkGlmvGe88XvtxHMf6WbFNLa7-m8TxjfNpywo9rBYnef1T2joyl3W8n6qSiLbm6e_BIAzrq9GDzlZnw9caLboxKgqrg5c80zD6i68eXisTUBQ").into(binding.wishesImg);
        binding.informTxt.setText("You have successfully completed your task");
    }

    private void handleTaskCompletedBtn() {
        binding.doCompletedBtn.setOnClickListener(view -> {
            task.doItem.status = "Completed";
            task.status = "Do Completed";
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("tasks").document(task.id)
                    .set(task)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(TaskDetailsActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TaskDetailsActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                        }
                    });
            binding.materialCardView.setVisibility(View.VISIBLE);
            binding.doCompletedBtn.setVisibility(View.GONE);
            binding.informTxt.setText("You have successfully completed your task");
            Picasso.get().load("https://lh4.googleusercontent.com/proxy/rwcYSyNHPdrUh70BGjqH9bNpQzMphBVK52yd8xkGlmvGe88XvtxHMf6WbFNLa7-m8TxjfNpywo9rBYnef1T2joyl3W8n6qSiLbm6e_BIAzrq9GDzlZnw9caLboxKgqrg5c80zD6i68eXisTUBQ").into(binding.wishesImg);
        });
    }
}