package com.improve10x.doordare.addandedittask;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.doordare.base.task.Dare;
import com.improve10x.doordare.base.task.Do;
import com.improve10x.doordare.base.task.Task;

public class AddTaskActivity extends BaseAddEditTaskActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Task");
        handleSaveBtn();
    }

    private void handleSaveBtn() {
        binding.saveBtn.setOnClickListener(view -> {
            String doTitle = binding.doTxt.getText().toString();
            String dareTitle = binding.dareTxt.getText().toString();
            if (isValidTask(doTitle, dareTitle, doDeadlineTimestamp)) {
                addTask(doTitle, dareTitle);
            } else {
                invalidTask(doTitle, dareTitle, doDeadlineTimestamp);
            }
        });
    }

    private void addTask(String doTitle, String dareTitle) {
        Task task = new Task();
        task.setDoItem(new Do());
        task.getDoItem().setTitle(doTitle);
        task.getDoItem().setStatus("Pending");
        task.getDoItem().setDeadlineTimestamp(doDeadlineTimestamp);
        task.setDare(new Dare());
        task.getDare().setTitle(dareTitle);
        task.getDare().setStatus("Not Needed");
        task.setCreatedTimestamp(System.currentTimeMillis());
        task.setStatus("Pending");
        addTask(task);
    }

    private void addTask(Task task) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        task.setId(db.collection("tasks").document().getId());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/tasks")
                .document(task.getId())
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Failed to Add Task");
                    }
                });
    }
}