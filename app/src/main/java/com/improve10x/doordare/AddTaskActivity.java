package com.improve10x.doordare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.doordare.databinding.ActivityAddTaskBinding;
import com.improve10x.doordare.pendingfragment.PendingTask;

public class AddTaskActivity extends AppCompatActivity {

    ActivityAddTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Task");
        handleSaveBtn();
    }

    private void handleSaveBtn() {
        binding.saveBtn.setOnClickListener(view -> {
            String task = binding.doTxt.getText().toString();
            String dare = binding.dareTxt.getText().toString();
            String deadline = binding.deadlineTxt.getText().toString();
            addTask(task, dare, deadline);
        });
    }

    private void addTask(String task, String dare, String deadline) {
        PendingTask pendingTask = new PendingTask();
        pendingTask.task = task;
        pendingTask.dare = dare;
        pendingTask.time = deadline;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .add(pendingTask)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddTaskActivity.this, "Successfully Added Task", Toast.LENGTH_SHORT).show();
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