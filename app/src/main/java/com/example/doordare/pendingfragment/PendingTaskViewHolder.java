package com.example.doordare.pendingfragment;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doordare.databinding.PendingItemBinding;

public class PendingTaskViewHolder extends RecyclerView.ViewHolder {

    PendingItemBinding binding;

    public PendingTaskViewHolder(PendingItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
