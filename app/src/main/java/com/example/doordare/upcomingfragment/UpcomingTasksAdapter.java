package com.example.doordare.upcomingfragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordare.databinding.UpcomingTaskItemBinding;

import java.util.ArrayList;

public class UpcomingTasksAdapter extends RecyclerView.Adapter<UpcomingTaskViewHolder> {

    private ArrayList<UpcomingTask> upcomingTasks;

    void setData(ArrayList<UpcomingTask> upcomingTasks) {
        this.upcomingTasks = upcomingTasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UpcomingTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UpcomingTaskItemBinding binding = UpcomingTaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        UpcomingTaskViewHolder upcomingTaskViewHolder = new UpcomingTaskViewHolder(binding);
        return upcomingTaskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingTaskViewHolder holder, int position) {
        UpcomingTask upcomingTask = upcomingTasks.get(position);
        holder.binding.timeTxt.setText(upcomingTask.time);
        holder.binding.dateTxt.setText(upcomingTask.date);
        holder.binding.monthAndYearTxt.setText(upcomingTask.monthAndYear);
        holder.binding.taskTxt.setText(upcomingTask.task);
        holder.binding.dareTxt.setText(upcomingTask.dare);
        holder.binding.reducedTimeTxt.setText(upcomingTask.reducedTime);
    }

    @Override
    public int getItemCount() {
        return upcomingTasks.size();
    }
}
