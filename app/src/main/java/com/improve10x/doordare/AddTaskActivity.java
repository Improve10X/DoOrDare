package com.improve10x.doordare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.doordare.base.BaseActivity;
import com.improve10x.doordare.databinding.ActivityAddTaskBinding;
import com.improve10x.doordare.base.task.Dare;
import com.improve10x.doordare.base.task.Do;
import com.improve10x.doordare.base.task.Task;
import com.noowenz.customdatetimepicker.CustomDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends BaseActivity implements CustomDateTimePicker.ICustomDateTimeListener {

    private ActivityAddTaskBinding binding;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handleCalendar();
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
            long deadline = Long.valueOf(deadlineTxt);
            if (doTitle.equals("") == false && dareTitle.equals("") == false && deadlineTxt.equals("") == false) {
                addTask(doTitle, dareTitle, deadline);
                finish();
            } else if (doTitle.equals("") == false && dareTitle.equals("") == true && deadlineTxt.equals("") == true) {
                showToast("Fill Dare and Deadline");
            } else if (doTitle.equals("") == false && dareTitle.equals("") == false && deadlineTxt.equals("") == true) {
                showToast("Fill Deadline");
            } else if (doTitle.equals("") == false && deadlineTxt.equals("") == false && dareTitle.equals("") == true) {
                showToast("Fill Dare");
            } else if (dareTitle.equals("") == false && doTitle.equals("") == true && deadlineTxt.equals("") == true) {
                showToast("Fill Do and Deadline");
            } else if (dareTitle.equals("") == false && deadlineTxt.equals("") == false && doTitle.equals("") == true) {
                showToast("Fill the Do");
            } else if (deadlineTxt.equals("") == false && doTitle.equals("") == true && dareTitle.equals("") == true) {
                showToast("Fill the Do and dare");
            } else {
                showToast("Fill Do, Dare and deadline");
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
                        showToast("Successfully Added Task");
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

    private void handleCalendar() {
        binding.calendarBtn.setOnClickListener(view -> {
            CustomDateTimePicker customDateTimePicker = new CustomDateTimePicker(this, this);
            customDateTimePicker.setDate(Calendar.getInstance());
            customDateTimePicker.showDialog();
        });
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onSet(@NonNull Dialog dialog, @NonNull Calendar calendar, @NonNull Date date, int i, @NonNull String s, @NonNull String s1, int i1, int i2, @NonNull String s2, @NonNull String s3, int i3, int i4, int i5, int i6, @NonNull String s4) {
            long timeInMillis = calendar.getTimeInMillis();
            String timeInMillisStr = String.valueOf(timeInMillis);
            binding.deadlineTxt.setText(timeInMillisStr);
    }
}