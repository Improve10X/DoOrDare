package com.improve10x.doordare.upcomingfragment;

import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.databinding.UpcomingTaskItemBinding;

public class UpcomingTaskViewHolder extends RecyclerView.ViewHolder {

    UpcomingTaskItemBinding binding;

    public UpcomingTaskViewHolder(UpcomingTaskItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
