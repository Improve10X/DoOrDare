package com.example.doordare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doordare.databinding.FragmentPastBinding;
import com.example.doordare.databinding.FragmentPendingBinding;

public class PendingFragment extends Fragment {

    private FragmentPendingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPendingBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void setupPendingTasksRv() {

    }
}