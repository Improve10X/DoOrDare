package com.example.doordare;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordare.databinding.PendingItemBinding;

import java.util.ArrayList;

public class PendingsAdapter extends RecyclerView.Adapter<PendingViewHolder> {

    private ArrayList<Pending> pendings;

    void setData(ArrayList<Pending> pendings) {
        this.pendings = pendings;
    }

    @NonNull
    @Override
    public PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PendingItemBinding binding = PendingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        PendingViewHolder pendingViewHolder = new PendingViewHolder(binding);
        return pendingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingViewHolder holder, int position) {
        Pending pending = pendings.get(position);
        holder.binding.timeTxt.setText(pending.time);
        holder.binding.dateTxt.setText(pending.date);
        holder.binding.monthAndYearTxt.setText(pending.monthAndYear);
        holder.binding.taskTxt.setText(pending.task);
        holder.binding.dareTxt.setText(pending.dare);
        holder.binding.reducedTimeTxt.setText(pending.reducedTime);
    }

    @Override
    public int getItemCount() {
        return pendings.size();
    }
}
