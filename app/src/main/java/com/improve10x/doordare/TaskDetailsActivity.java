package com.improve10x.doordare;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.doordare.base.BaseActivity;
import com.improve10x.doordare.base.Constants;
import com.improve10x.doordare.databinding.ActivityTaskDetailsBinding;
import com.improve10x.doordare.base.task.Task;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDetailsActivity extends BaseActivity {

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
        binding.doTxt.setText(task.getDoItem().getTitle());
        binding.dareTxt.setText(task.getDare().getTitle());
        long deadline = task.getDoItem().getDeadlineTimestamp();
        Date date = new Date(deadline);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm aa");
        String deadlineTxt = simpleDateFormat.format(date);
        binding.deadlineTxt.setText(deadlineTxt);
        binding.statusTxt.setText(task.getStatus());
        if (task.getStatus().equalsIgnoreCase("Pending")) {
            pendingStatus();
        } else if (task.getStatus().equalsIgnoreCase("Do Completed")) {
            doCompletedStatus();
        } else if (task.getStatus().equalsIgnoreCase("Dare Completed")) {
            dareCompletedStatus();
        }
    }

    private void pendingStatus() {
        binding.doCompletedBtn.setVisibility(View.VISIBLE);
        binding.materialCardView.setVisibility(View.GONE);
        handleTaskCompletedBtn();
        long currentTimeInMillis = System.currentTimeMillis();
        if (currentTimeInMillis < task.getDoItem().getDeadlineTimestamp()) {
            binding.statusTxt.setText(task.getStatus());
        } else if (currentTimeInMillis >= task.getDoItem().getDeadlineTimestamp()) {
            binding.statusTxt.setText("'Do' not completed");
            doNotCompletedStatus();
        }
    }

    private void doCompletedStatus() {
        binding.materialCardView.setVisibility(View.VISIBLE);
        binding.doCompletedBtn.setVisibility(View.GONE);
        Picasso.get().load("https://lh4.googleusercontent.com/proxy/rwcYSyNHPdrUh70BGjqH9bNpQzMphBVK52yd8xkGlmvGe88XvtxHMf6WbFNLa7-m8TxjfNpywo9rBYnef1T2joyl3W8n6qSiLbm6e_BIAzrq9GDzlZnw9caLboxKgqrg5c80zD6i68eXisTUBQ").into(binding.wishesImg);
        binding.informTxt.setText("You have successfully completed your task");
    }

    private void doNotCompletedStatus() {
        binding.materialCardView.setVisibility(View.VISIBLE);
        Picasso.get().load("https://lh6.googleusercontent.com/proxy/32dDmknRg0Ji1f9ZrfKwfH2OmwSC-9aeGdgrtu0YH6isG0Gzs74JNBFRLXXJLXxm2D045G3tSf2Jnw").into(binding.wishesImg);
        binding.informTxt.setText("You haven't completed the 'Do' task \uD83D\uDE46\uD83C\uDFFB\u200D♂️, so 'Dare' needs to be completed\uD83E\uDD37\uD83C\uDFFB\u200D♂️");
        binding.dareCompletedBtn.setVisibility(View.VISIBLE);
        binding.doCompletedBtn.setVisibility(View.GONE);
        handleDareCompletedBtn();
    }

    private void dareCompletedStatus() {
        binding.materialCardView.setVisibility(View.VISIBLE);
        binding.dareCompletedBtn.setVisibility(View.GONE);
        binding.informTxt.setText("If you know you can do better.. then do better.");
        Picasso.get().load("https://lh4.googleusercontent.com/proxy/OwzUIh7P-Thx34_WKd5bwJfn0hLRmSVUK3nb3I-ZIcuQ9EHnXZN6-tYbHC-lP_NJD4mNHuUAtcJXQYIfky8WrUM1zvDvEPtbFF6vGwbEg-4z").into(binding.wishesImg);
    }

    private void handleTaskCompletedBtn() {
        binding.doCompletedBtn.setOnClickListener(view -> {
            task.getDoItem().setStatus("Completed");
            task.setStatus("Do Completed");
            updateTask(task);
            doCompletedStatus();
        });
    }

    private void handleDareCompletedBtn() {
        binding.dareCompletedBtn.setOnClickListener(view -> {
            task.getDare().setStatus("completed");
            task.setStatus("Dare Completed");
            updateTask(task);
            dareCompletedStatus();
        });
    }

    private void updateTask(Task task) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/tasks")
                .document(task.getId())
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Failed to update");
                    }
                });
    }
}