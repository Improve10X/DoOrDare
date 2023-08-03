package com.improve10x.doordare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.improve10x.doordare.databinding.ActivityUserAccessBinding;

public class UserAccessActivity extends AppCompatActivity {

    private ActivityUserAccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleLoginBtn();
        handleGuestBtn();
    }

    private void handleGuestBtn() {

    }

    private void handleLoginBtn() {
        binding.loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}