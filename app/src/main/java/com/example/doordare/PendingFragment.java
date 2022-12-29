package com.example.doordare;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doordare.databinding.FragmentPastBinding;
import com.example.doordare.databinding.FragmentPendingBinding;

import java.util.ArrayList;

public class PendingFragment extends Fragment {

    private FragmentPendingBinding binding;
    private ArrayList<Pending> pendings;
    private PendingsAdapter pendingsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPendingBinding.inflate(getLayoutInflater());
        setupData();
        setupPendingsAdapter();
        setupPendingTasksRv();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void setupData() {
        pendings = new ArrayList<>();
        Pending pending = new Pending("1", "Do : some Task", "Dare : Some Dare", "10AM", "29", "Dec, 2022", "1 hr left");
        pendings.add(pending);
        pendings.add(pending);
        pendings.add(pending);
        pendings.add(pending);
        Pending pending1 = new Pending("1", "Do : some Task Task Task Task Task v TaskTaskTaskTaskTaskTask TaskTaskTask ", "Dare : Some Dare Some Dare Some Dare Some Dare Some Dare Some Dare Some Some DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareSome DareDare Some DareSome DareSome Dare Some Dare Some Dare", "10AM", "29", "Dec, 2022", "1 hr left");
        pendings.add(pending1);
    }

    private void setupPendingsAdapter() {
        pendingsAdapter = new PendingsAdapter();
        pendingsAdapter.setData(pendings);
    }

    private void setupPendingTasksRv() {
        binding.pendingTasksRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.pendingTasksRv.setAdapter(pendingsAdapter);
    }
}