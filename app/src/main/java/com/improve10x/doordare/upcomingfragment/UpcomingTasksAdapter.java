package com.improve10x.doordare.upcomingfragment;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.OnEditActionListener;
import com.improve10x.doordare.base.OnItemActionListener;
import com.improve10x.doordare.base.task.Task;
import com.improve10x.doordare.databinding.UpcomingTaskItemBinding;
import com.improve10x.doordare.pendingfragment.PendingTaskViewHolder;
import com.improve10x.doordare.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UpcomingTasksAdapter extends RecyclerView.Adapter<UpcomingTaskViewHolder> {

    private List<Task> tasks;
    private OnItemActionListener onItemActionListener;
    private OnEditActionListener onEditActionListener;

    void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    void setOnEditActionListener(OnEditActionListener onEditActionListener) {
        this.onEditActionListener = onEditActionListener;
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
        Task task = tasks.get(position);
        String doHtml = "<b>Do :</b> " + task.getDoItem().getTitle();
        holder.binding.taskTxt.setText(Html.fromHtml(doHtml));
        String dareHtml = "<b>Dare :</b> " + task.getDare().getTitle();
        holder.binding.dareTxt.setText(Html.fromHtml(dareHtml));
        setTime(holder, task);
        holder.binding.getRoot().setOnClickListener(view -> {
            onItemActionListener.onItemClicked(task);
        });
        showEditBtn(holder);
        holder.binding.editBtn.setOnClickListener(v -> {
            onEditActionListener.onEdit(task);
        });
        setReducedTime(holder, task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private void setTime(UpcomingTaskViewHolder holder, Task task) {
        long doTimestamp = task.getDoItem().getDeadlineTimestamp();
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

    private void setReducedTime(UpcomingTaskViewHolder holder, Task task) {
        long currentTimeInMillis = System.currentTimeMillis();
        long diffInMillis = task.getDoItem().getDeadlineTimestamp() - currentTimeInMillis;
        String timeLeft = DateUtils.getAdvancedTimeLeftText(diffInMillis);
        holder.binding.reducedTimeTxt.setText(timeLeft + "left");
    }

    private void showEditBtn(UpcomingTaskViewHolder holder) {
        holder.binding.getRoot().setOnLongClickListener(v -> {
            holder.binding.editBtn.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                holder.binding.editBtn.setVisibility(View.GONE);
            }, 3000);
            return true;
        });
    }
}