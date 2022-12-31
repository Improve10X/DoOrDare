package com.improve10x.doordare.pendingfragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.Task;
import com.improve10x.doordare.databinding.PendingItemBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PendingTasksAdapter extends RecyclerView.Adapter<PendingTaskViewHolder> {

    private List<Task> tasks;
    private OnItemActionListener onItemActionListener;

    void setData(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
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
        Task task = tasks.get(position);
        holder.binding.taskTxt.setText(task.doItem.title);
        holder.binding.dareTxt.setText(task.dare.title);
        long doTimestamp = task.doItem.deadlineTimestamp;
        Date date = new Date(doTimestamp);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh aa");
        String timeText = timeFormat.format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String dateText = dateFormat.format(date);
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM yyyy");
        String monthYear = monthYearFormat.format(date);
        holder.binding.timeTxt.setText(timeText);
        holder.binding.dateTxt.setText(dateText);
        holder.binding.monthAndYearTxt.setText(monthYear);
        // TODO : need to calculate the time left for do
        //holder.binding.reducedTimeTxt.setText(pendingTask.reducedTime);
        holder.binding.getRoot().setOnClickListener(view -> {
            onItemActionListener.onItemClicked(task);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
