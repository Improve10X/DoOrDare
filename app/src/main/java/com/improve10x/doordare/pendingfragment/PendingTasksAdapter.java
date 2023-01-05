package com.improve10x.doordare.pendingfragment;

import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.base.OnItemActionListener;
import com.improve10x.doordare.base.task.Task;
import com.improve10x.doordare.databinding.PendingItemBinding;
import com.improve10x.doordare.utils.DateUtils;

import java.text.SimpleDateFormat;
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
        String doHtml = "<b>Do :</b> " + task.doItem.title;
        holder.binding.taskTxt.setText(Html.fromHtml(doHtml));
        String dareHtml = "<b>Dare :</b> " + task.dare.title;
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

    private void timeSetting(PendingTaskViewHolder holder, Task task) {
        long doTimestamp = task.doItem.deadlineTimestamp;
        Date date = new Date(doTimestamp);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        String timeText = timeFormat.format(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String dateText = dateFormat.format(date);
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM yyyy");
        String monthYear = monthYearFormat.format(date);
        holder.binding.timeTxt.setText(timeText);
        holder.binding.dateTxt.setText(dateText);
        holder.binding.monthAndYearTxt.setText(monthYear);
    }

    private void setReducedTimeAndColors(PendingTaskViewHolder holder, Task task) {
        long currentTimeInMillis = System.currentTimeMillis();
        long diffInMillis = task.doItem.deadlineTimestamp - currentTimeInMillis;
        if(diffInMillis < 3600000) {
            holder.binding.reducedTimeTxt.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#FF0000"));
        }else if(diffInMillis < 10800000) {
            holder.binding.reducedTimeTxt.setBackgroundColor(Color.parseColor("#FF6666"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#FF6666"));
        }
        String timeLeft = DateUtils.getAdvancedTimeLeftText(diffInMillis);
        if (timeLeft.isEmpty() == false) {
            holder.binding.reducedTimeTxt.setText(timeLeft + "left");
        } else {
            holder.binding.reducedTimeTxt.setText("'Do' is not finished so complete 'Dare'");
            task.status = "'Do' not completed";
        }
    }
}
