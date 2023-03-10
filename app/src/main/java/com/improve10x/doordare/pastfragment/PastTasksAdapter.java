package com.improve10x.doordare.pastfragment;

import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.base.OnItemActionListener;
import com.improve10x.doordare.base.task.Task;
import com.improve10x.doordare.databinding.PastTaskItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PastTasksAdapter extends RecyclerView.Adapter<PastTaskViewHolder> {

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
    public PastTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PastTaskItemBinding binding = PastTaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        PastTaskViewHolder pastTaskViewHolder = new PastTaskViewHolder(binding);
        return pastTaskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PastTaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        String doHtml = "<b>Do :</b> " + task.doItem.title;
        holder.binding.taskTxt.setText(Html.fromHtml(doHtml));
        String dareHtml = "<b>Do :</b> " + task.dare.title;
        holder.binding.dareTxt.setText(Html.fromHtml(dareHtml));
        timeSetting(holder, task);
        holder.binding.getRoot().setOnClickListener(view -> {
            onItemActionListener.onItemClicked(task);
        });
        setReducedTimeAndColors(holder, task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private void timeSetting(PastTaskViewHolder holder, Task task) {
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
    }

    private void setReducedTimeAndColors(PastTaskViewHolder holder, Task task) {
        if (task.status.equalsIgnoreCase("Do Completed")) {
            holder.binding.reducedTimeTxt.setText("Do Completed");
            holder.binding.reducedTimeTxt.setBackgroundColor(Color.parseColor("#2B7A0B"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#2B7A0B"));
        } else {
            holder.binding.reducedTimeTxt.setText("Dare Completed");
            holder.binding.reducedTimeTxt.setBackgroundColor(Color.parseColor("#8BC34A"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#8BC34A"));
        }
    }
}
