package com.improve10x.doordare.pendingfragment;

import android.os.CountDownTimer;

import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.databinding.PendingItemBinding;
import com.improve10x.doordare.utils.DateUtils;

public class PendingTaskViewHolder extends RecyclerView.ViewHolder {

    PendingItemBinding binding;

    public PendingTaskViewHolder(PendingItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
