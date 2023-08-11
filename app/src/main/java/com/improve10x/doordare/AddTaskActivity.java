package com.improve10x.doordare;

import androidx.annotation.NonNull;

import android.app.Dialog;
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
    long doDeadlineTimestamp = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().hasExtra(Constants.KEY_TASK)) {
            task = (Task) getIntent().getSerializableExtra(Constants.KEY_TASK);
            showData();
        }
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
            if (doTitle.equals("") == false && dareTitle.equals("") == false && doDeadlineTimestamp != 0) {
                handleAddTask(doTitle, dareTitle);
            } else if (doTitle.equals("") == false && dareTitle.equals("") == true && doDeadlineTimestamp == 0) {
                showToast("Fill Dare and Deadline");
            } else if (doTitle.equals("") == false && dareTitle.equals("") == false && doDeadlineTimestamp == 0) {
                showToast("Fill Deadline");
            } else if (doTitle.equals("") == false && doDeadlineTimestamp != 0 && dareTitle.equals("") == true) {
                showToast("Fill Dare");
            } else if (dareTitle.equals("") == false && doTitle.equals("") == true && doDeadlineTimestamp == 0) {
                showToast("Fill Do and Deadline");
            } else if (dareTitle.equals("") == false && doDeadlineTimestamp != 0 && doTitle.equals("") == true) {
                showToast("Fill the Do");
            } else if (doDeadlineTimestamp != 0 && doTitle.equals("") == true && dareTitle.equals("") == true) {
                showToast("Fill the Do and dare");
            } else {
                showToast("Fill Do, Dare and deadline");
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
        doDeadlineTimestamp = calendar.getTimeInMillis();
        Date date1 = new Date(calendar.getTimeInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
        String displayTime = dateFormat.format(date1);
        if (doDeadlineTimestamp > System.currentTimeMillis()) {
            binding.errorTxt.setVisibility(View.GONE);
            binding.deadlineTxt.setText(displayTime);
        } else {
            doDeadlineTimestamp = 0;
            binding.deadlineTxt.setText("");
            binding.errorTxt.setVisibility(View.VISIBLE);
        }
    }

    public boolean isAllSpaces(String text) {
        int spaceChars = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                spaceChars++;
            }
        }
        return spaceChars == text.length();
    }

    private void handleAddTask(String doTitle, String dareTitle) {
        if (!isAllSpaces(doTitle) && !isAllSpaces(dareTitle)) {
            addTask(doTitle, dareTitle);
            finish();
        } else {
            if (isAllSpaces(doTitle) && isAllSpaces(dareTitle)) {
                binding.doTxt.setText("");
                binding.dareTxt.setText("");
                showToast("Do and dare not including all spaces");
            } else if (isAllSpaces(doTitle) && !isAllSpaces(dareTitle)) {
                binding.doTxt.setText("");
                showToast("Do not including all spaces");
            } else if (!isAllSpaces(doTitle) && isAllSpaces(dareTitle)){
                binding.dareTxt.setText("");
                showToast("Dare not including all spaces");
            }
        }
    }

    private void showData() {
        binding.doTxt.setText(task.getDoItem().getTitle());
        binding.dareTxt.setText(task.getDare().getTitle());
        Date date = new Date(task.getDare().getDeadlineTimestamp());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
        String displayDeadline = dateFormat.format(date);
        binding.deadlineTxt.setText(displayDeadline);
    }
}