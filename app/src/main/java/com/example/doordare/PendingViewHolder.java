package com.example.doordare;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordare.databinding.PendingItemBinding;

public class PendingViewHolder extends RecyclerView.ViewHolder {

    PendingItemBinding binding;

    public PendingViewHolder(PendingItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
