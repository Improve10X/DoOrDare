package com.improve10x.doordare.addandedittask;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.improve10x.doordare.base.BaseActivity;
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

    protected boolean isValidTask(String doTitle, String dareTitle, long doDeadlineTimestamp) {
        return !doTitle.trim().isEmpty() && !dareTitle.trim().equals("") && doDeadlineTimestamp != 0;
    }

    protected void invalidTask(String doTitle, String dareTitle, long doDeadlineTimestamp) {
        if (doTitle.equals("") == false && dareTitle.equals("") == true && doDeadlineTimestamp == 0) {
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
        } else if (isAllSpaces(doTitle) && isAllSpaces(dareTitle)) {
            binding.doTxt.setText("");
            binding.dareTxt.setText("");
            showToast("Do and dare not including all spaces");
        } else if (isAllSpaces(doTitle) && !isAllSpaces(dareTitle)) {
            binding.doTxt.setText("");
            showToast("Do not including all spaces");
        } else if (!isAllSpaces(doTitle) && isAllSpaces(dareTitle)){
            binding.dareTxt.setText("");
            showToast("Dare not including all spaces");
        } else {
            showToast("Fill Do, Dare and deadline");
        }
    }

    protected boolean isAllSpaces(String text) {
        return text.trim().length() == 0;
    }
}