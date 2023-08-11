package com.improve10x.doordare.pendingfragment;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.OnEditActionListener;
import com.improve10x.doordare.base.OnItemActionListener;
import com.improve10x.doordare.base.task.Task;
import com.improve10x.doordare.databinding.PendingItemBinding;
import com.improve10x.doordare.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PendingTasksAdapter extends RecyclerView.Adapter<PendingTaskViewHolder> {

    private List<Task> tasks;
    private CountDownTimer[] timers;
    private OnItemActionListener onItemActionListener;
    private OnTimeActionListener onTimeActionListener;
    private OnEditActionListener onEditActionListener;

    void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        cancelTimers();
        timers = new CountDownTimer[tasks.size()];
        notifyDataSetChanged();
    }

    public void cancelTimers() {
        if(timers != null) {
            for (int i=0; i< timers.length; i++) {
                if (timers[i] != null) {
                    timers[i].cancel();
                }
            }
        }
    }

    void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    void setOnTimeActionListener(OnTimeActionListener onTimeActionListener) {
        this.onTimeActionListener = onTimeActionListener;
    }

    void setOnEditActionListener(OnEditActionListener onEditActionListener) {
        this.onEditActionListener = onEditActionListener;
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
        String doHtml = "<b>Do :</b> " + task.getDoItem().getTitle();
        holder.binding.taskTxt.setText(Html.fromHtml(doHtml));
        String dareHtml = "<b>Dare :</b> " + task.getDare().getTitle();
        holder.binding.dareTxt.setText(Html.fromHtml(dareHtml));
        setTime(holder, task);
        holder.binding.getRoot().setOnClickListener(view -> {
            onItemActionListener.onItemClicked(task);
        });
        holder.binding.editBtn.setOnClickListener(view -> {
            onEditActionListener.onEdit(task);
        });
        setReducedTimeAndColors(holder, task, position);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private void setTime(PendingTaskViewHolder holder, Task task) {
        long doTimestamp = task.getDoItem().getDeadlineTimestamp();
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

    private void setReducedTimeAndColors(PendingTaskViewHolder holder, Task task, int position) {
        long currentTimeInMillis = System.currentTimeMillis();
        long diffInMillis = task.getDoItem().getDeadlineTimestamp() - currentTimeInMillis;
        if (diffInMillis < 3600000) {
            holder.binding.reducedTimeTxt.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#FF0000"));
        } else if (diffInMillis < 10800000) {
            holder.binding.reducedTimeTxt.setBackgroundColor(Color.parseColor("#FF6666"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#FF6666"));
        } else if (diffInMillis > 10800000) {
            holder.binding.reducedTimeTxt.setBackgroundColor(Color.parseColor("#F57C00"));
            holder.binding.materialCardView.setStrokeColor(Color.parseColor("#F57C00"));
        }
        setReducedTime(holder, task, position, diffInMillis);
    }

    private void setReducedTime(PendingTaskViewHolder holder, Task task, int position, long diffInMillis) {
        if (diffInMillis <= 0) {
            holder.binding.reducedTimeTxt.setText("'Do' is not finished so complete 'Dare'");
        } else if (diffInMillis < 10 * 60 *1000) {
            if(timers[position] == null) {
                timers[position] = getTimer(diffInMillis, holder, task);
                timers[position].start();
            }
        } else {
            String timeLeft = DateUtils.getAdvancedTimeLeftText(diffInMillis);
            holder.binding.reducedTimeTxt.setText(timeLeft + "left");
        }
    }

    public CountDownTimer getTimer(long diffInMillis, PendingTaskViewHolder holder, Task task) {
        return new CountDownTimer(diffInMillis, 1000) {
            @Override
            public void onTick(long l) {
                String timeLeft = DateUtils.getAdvancedTimeLeftText(l);
                holder.binding.reducedTimeTxt.setText(timeLeft + "left");
            }

            @Override
            public void onFinish() {
                holder.binding.reducedTimeTxt.setText("'Do' is not finished so complete 'Dare'");
                onTimeActionListener.showNotification("You haven't complete the do, Pls complete dare", task.getDoItem().getTitle());            }
        };
    }
}