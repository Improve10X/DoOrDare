package com.improve10x.doordare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.improve10x.doordare.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        showAnimation();
        checkUserLogin();
    }

    private void checkUserLogin() {
        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void showAnimation() {
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, android.R.anim.slide_in_left);
        binding.doOrDareImg.startAnimation(animation);
        Glide.with(SplashActivity.this)
                .load("https://images.squarespace-cdn.com/content/v1/5ce86c3589cdd000019bb991/506cd0da-bae1-46d1-95b2-fef141cd3e7d/loading+gif+1.gif")
                .into(binding.loadingImg);
        binding.loadingImg.startAnimation(animation);
    }
}
//https://media.tenor.com/DWAVxZzFBf8AAAAC/friends-loading.gif