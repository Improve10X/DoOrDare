package com.improve10x.doordare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.doordare.base.BaseActivity;
import com.improve10x.doordare.base.Constants;
import com.improve10x.doordare.databinding.ActivityTaskDetailsBinding;
import com.improve10x.doordare.base.task.Task;
import com.improve10x.doordare.utils.DateUtils;
import com.squareup.picasso.Picasso;

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
        binding.doTxt.setText(task.doItem.title);
        binding.dareTxt.setText(task.dare.title);
        long deadline = task.doItem.deadlineTimestamp;
        Date date = new Date(deadline);
        String deadlineStr = String.valueOf(date);
        binding.deadlineTxt.setText(deadlineStr);
        binding.statusTxt.setText(task.status);
        if (task.status.equalsIgnoreCase("Pending")) {
            pendingStatus();
        } else if (task.status.equalsIgnoreCase("Do Completed")) {
            doCompletedStatus();
        } else if (task.status.equalsIgnoreCase("Dare Completed")) {
            dareCompletedStatus();
        }
    }

    private void pendingStatus() {
        binding.doCompletedBtn.setVisibility(View.VISIBLE);
        binding.materialCardView.setVisibility(View.GONE);
        handleTaskCompletedBtn();
        long currentTimeInMillis = System.currentTimeMillis();
        if (currentTimeInMillis < task.doItem.deadlineTimestamp) {
            binding.statusTxt.setText(task.status);
        } else if (currentTimeInMillis >= task.doItem.deadlineTimestamp) {
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
            task.doItem.status = "Completed";
            task.status = "Do Completed";
            updateTask(task);
            doCompletedStatus();
        });
    }

    private void handleDareCompletedBtn() {
        binding.dareCompletedBtn.setOnClickListener(view -> {
            task.dare.status = "completed";
            task.status = "Dare Completed";
            updateTask(task);
            dareCompletedStatus();
        });
    }

    private void updateTask(Task task) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/tasks")
                .document(task.id)
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showToast("Updated");
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