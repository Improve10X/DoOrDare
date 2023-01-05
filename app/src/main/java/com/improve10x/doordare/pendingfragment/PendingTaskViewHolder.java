package com.improve10x.doordare.pendingfragment;

import android.os.CountDownTimer;

import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.doordare.databinding.PendingItemBinding;
import com.improve10x.doordare.utils.DateUtils;

public class PendingTaskViewHolder extends RecyclerView.ViewHolder {

    PendingItemBinding binding;
    CountDownTimer timer;

    public PendingTaskViewHolder(PendingItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setupTimer(long diffInMillis) {
        if(timer == null) {
            timer = new CountDownTimer(diffInMillis, 1000) {
                @Override
                public void onTick(long l) {
                    String timeLeft = DateUtils.getAdvancedTimeLeftText(l);
                    binding.reducedTimeTxt.setText(timeLeft + "left");
                }

                @Override
                public void onFinish() {
                    binding.reducedTimeTxt.setText("'Do' is not finished so complete 'Dare'");
                }
            };
            timer.start();
        }
    }
}
