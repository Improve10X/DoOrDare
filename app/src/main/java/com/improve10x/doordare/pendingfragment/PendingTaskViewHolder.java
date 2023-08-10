package com.improve10x.doordare.pendingfragment;

import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.databinding.PendingItemBinding;

public class PendingTaskViewHolder extends RecyclerView.ViewHolder {
    PendingItemBinding binding;

    public PendingTaskViewHolder(PendingItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
