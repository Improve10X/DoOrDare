package com.improve10x.doordare.pastfragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.databinding.PastTaskItemBinding;

import java.util.ArrayList;

public class PastTasksAdapter extends RecyclerView.Adapter<PastTaskViewHolder> {

    private ArrayList<PastTask> pastTasks;

    void setData(ArrayList<PastTask> pastTasks) {
        this.pastTasks = pastTasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PastTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PastTaskItemBinding binding = PastTaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        PastTaskViewHolder pastTaskViewHolder = new PastTaskViewHolder(binding);
        return pastTaskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PastTaskViewHolder holder, int position) {
        PastTask pastTask = pastTasks.get(position);
        holder.binding.timeTxt.setText(pastTask.time);
        holder.binding.dateTxt.setText(pastTask.date);
        holder.binding.monthAndYearTxt.setText(pastTask.monthAndYear);
        holder.binding.taskTxt.setText(pastTask.task);
        holder.binding.dareTxt.setText(pastTask.dare);
        holder.binding.reducedTimeTxt.setText(pastTask.reducedTime);
    }

    @Override
    public int getItemCount() {
        return pastTasks.size();
    }
}
