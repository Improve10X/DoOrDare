package com.improve10x.doordare;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.improve10x.doordare.connectguesttomobilelogin.ConnectMobileNumberDialog;
import com.improve10x.doordare.ui.main.SectionsPagerAdapter;
import com.improve10x.doordare.databinding.ActivityHomeBinding;

public class
HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Do / Dare");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(!user.isAnonymous()) {
            MenuItem item = menu.findItem(R.id.connect_with_mobile_number);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            showSignOutDialog();
            return true;
        } else if (item.getItemId() == R.id.connect_with_mobile_number) {
            new ConnectMobileNumberDialog().show(getSupportFragmentManager(), "Connect with Mobile Number");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(HomeActivity.this, UserAccessActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void showSignOutDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure you want to Logout?");
        alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            signOut();
        });
        alertDialog.setNegativeButton("No", (dialogInterface, i) -> {
            alertDialog.setCancelable(true);
        });
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }
}
