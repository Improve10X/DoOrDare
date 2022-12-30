package com.improve10x.doordare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TaskDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        getSupportActionBar().setTitle("Task Details");
    }
}