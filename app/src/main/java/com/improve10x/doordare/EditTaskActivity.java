package com.improve10x.doordare;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.doordare.base.Constants;
import com.improve10x.doordare.base.task.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTaskActivity extends BaseAddEditTaskActivity {

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Task");
        if (getIntent().hasExtra(Constants.KEY_TASK)) {
            task = (Task) getIntent().getSerializableExtra(Constants.KEY_TASK);
            showData();
        }
        handleEdit();
    }

    private void handleEdit() {
        binding.saveBtn.setOnClickListener(v -> {
            updateTask(task);
        });
    }

    private void showData() {
        binding.saveBtn.setText("Edit");
        binding.doTxt.setText(task.getDoItem().getTitle());
        binding.dareTxt.setText(task.getDare().getTitle());
        Date date = new Date(task.getDoItem().getDeadlineTimestamp());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
        String displayDeadline = dateFormat.format(date);
        binding.deadlineTxt.setText(displayDeadline);
    }

    private void updateTask(Task task) {
        task.getDoItem().setTitle(binding.doTxt.getText().toString());
        task.getDare().setTitle(binding.dareTxt.getText().toString());
        task.getDoItem().setDeadlineTimestamp(doDeadlineTimestamp);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                        showToast("Failed to update");
                    }
                });
    }
}