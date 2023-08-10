package com.improve10x.doordare;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.improve10x.doordare.base.BaseActivity;
import com.improve10x.doordare.databinding.ActivityUserAccessBinding;

public class UserAccessActivity extends BaseActivity {

    private ActivityUserAccessBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserAccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Login");
        firebaseAuth = FirebaseAuth.getInstance();
        handleLoginBtn();
        handleGuestBtn();
    }

    private void handleGuestBtn() {
        binding.guestBtn.setOnClickListener(v -> {
            firebaseAuth.signInAnonymously()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(this, HomeActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(e -> {
                        showToast("Guest Login Failed");
                    });
        });
    }

    private void handleLoginBtn() {
        binding.loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}