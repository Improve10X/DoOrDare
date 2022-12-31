package com.improve10x.doordare.pastfragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.Task;
import com.improve10x.doordare.databinding.PastTaskItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PastTasksAdapter extends RecyclerView.Adapter<PastTaskViewHolder> {

    private List<Task> tasks;

    void setData(List<Task> tasks) {
        this.tasks = tasks;
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
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
