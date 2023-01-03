package com.improve10x.doordare.base.task;

import java.io.Serializable;

public class Do implements Serializable {
    public String title;
    public String status;
    public long completedTimestamp;
    public long deadlineTimestamp;
}
