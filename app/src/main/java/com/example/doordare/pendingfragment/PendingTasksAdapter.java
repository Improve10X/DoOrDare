package com.example.doordare.pendingfragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordare.databinding.PendingItemBinding;

import java.util.ArrayList;

public class PendingTasksAdapter extends RecyclerView.Adapter<PendingTaskViewHolder> {

    private ArrayList<PendingTask> pendings;

    void setData(ArrayList<PendingTask> pendings) {
        this.pendings = pendings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PendingTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PendingItemBinding binding = PendingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        PendingTaskViewHolder pendingTaskViewHolder = new PendingTaskViewHolder(binding);
        return pendingTaskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingTaskViewHolder holder, int position) {
        PendingTask pendingTask = pendings.get(position);
        holder.binding.timeTxt.setText(pendingTask.time);
        holder.binding.dateTxt.setText(pendingTask.date);
        holder.binding.monthAndYearTxt.setText(pendingTask.monthAndYear);
        holder.binding.taskTxt.setText(pendingTask.task);
        holder.binding.dareTxt.setText(pendingTask.dare);
        holder.binding.reducedTimeTxt.setText(pendingTask.reducedTime);
    }

    @Override
    public int getItemCount() {
        return pendings.size();
    }
}
