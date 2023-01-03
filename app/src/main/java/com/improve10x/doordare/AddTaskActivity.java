package com.improve10x.doordare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.doordare.databinding.ActivityAddTaskBinding;
import com.improve10x.doordare.base.task.Dare;
import com.improve10x.doordare.base.task.Do;
import com.improve10x.doordare.base.task.Task;

public class AddTaskActivity extends AppCompatActivity {

    ActivityAddTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handleSaveBtn();
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

    private void handleSaveBtn() {
        binding.saveBtn.setOnClickListener(view -> {
            String doTitle = binding.doTxt.getText().toString();
            String dareTitle = binding.dareTxt.getText().toString();
            String deadlineTxt = binding.deadlineTxt.getText().toString();
            long dummyDeadline = 1672486200000L;
            if (doTitle.equals("") == false && dareTitle.equals("") == false && deadlineTxt.equals("") == false) {
                addTask(doTitle, dareTitle, dummyDeadline);
                finish();
            } else if (doTitle.equals("") == false && dareTitle.equals("") == true && deadlineTxt.equals("") == true) {
                Toast.makeText(this, "Fill Dare and Deadline", Toast.LENGTH_SHORT).show();
            } else if (doTitle.equals("") == false && dareTitle.equals("") == false && deadlineTxt.equals("") == true) {
                Toast.makeText(this, "Fill Deadline", Toast.LENGTH_SHORT).show();
            } else if (doTitle.equals("") == false && deadlineTxt.equals("") == false && dareTitle.equals("") == true) {
                Toast.makeText(this, "Fill Dare", Toast.LENGTH_SHORT).show();
            } else if (dareTitle.equals("") == false && doTitle.equals("") == true && deadlineTxt.equals("") == true) {
                Toast.makeText(this, "Fill Do and Deadline", Toast.LENGTH_SHORT).show();
            } else if (dareTitle.equals("") == false && deadlineTxt.equals("") == false && doTitle.equals("") == true) {
                Toast.makeText(this, "Fill the Do", Toast.LENGTH_SHORT).show();
            } else if (deadlineTxt.equals("") == false && doTitle.equals("") == true && dareTitle.equals("") == true) {
                Toast.makeText(this, "Fill the Do and dare", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Fill Do, Dare and deadline", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTask(String doTitle, String dareTitle, long deadline) {
        Task task = new Task();
        task.doItem = new Do();
        task.doItem.title = doTitle;
        task.doItem.status = "Pending";
        task.doItem.deadlineTimestamp = deadline;
        task.dare = new Dare();
        task.dare.title = dareTitle;
        task.dare.status = "Not Needed";
        task.createdTimestamp = System.currentTimeMillis();
        task.status = "Pending";
        addTask(task);
    }

    private void addTask(Task task) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        task.id = db.collection("tasks").document().getId();
        db.collection("tasks")
                .document(task.id)
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddTaskActivity.this, "Successfully Added Task", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddTaskActivity.this, "Failed to Add Task", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}