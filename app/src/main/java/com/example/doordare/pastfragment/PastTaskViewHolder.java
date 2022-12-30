package com.example.doordare.pastfragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordare.databinding.PastTaskItemBinding;

public class PastTaskViewHolder extends RecyclerView.ViewHolder {

    PastTaskItemBinding binding;

    public PastTaskViewHolder(PastTaskItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
