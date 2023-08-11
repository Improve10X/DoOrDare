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
import com.improve10x.doordare.base.task.Dare;
import com.improve10x.doordare.base.task.Do;
import com.improve10x.doordare.base.task.Task;
import com.improve10x.doordare.databinding.ActivityBaseAddEditTaskBinding;
import com.noowenz.customdatetimepicker.CustomDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BaseAddEditTaskActivity extends BaseActivity implements CustomDateTimePicker.ICustomDateTimeListener {

    protected ActivityBaseAddEditTaskBinding binding;
    protected long doDeadlineTimestamp = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseAddEditTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handleCalendar();
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

    protected boolean isAllSpaces(String text) {
        int spaceChars = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                spaceChars++;
            }
        }
        return spaceChars == text.length();
    }
}