package com.example.doordare.upcomingfragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordare.databinding.UpcomingTaskItemBinding;

public class UpcomingTaskViewHolder extends RecyclerView.ViewHolder {

    UpcomingTaskItemBinding binding;

    public UpcomingTaskViewHolder(UpcomingTaskItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
