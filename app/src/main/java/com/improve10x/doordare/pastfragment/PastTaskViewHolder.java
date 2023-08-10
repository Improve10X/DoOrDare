package com.improve10x.doordare.pastfragment;

import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.databinding.PastTaskItemBinding;

public class PastTaskViewHolder extends RecyclerView.ViewHolder {

    PastTaskItemBinding binding;

    public PastTaskViewHolder(PastTaskItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}