package com.improve10x.doordare;

import java.io.Serializable;

public class Task implements Serializable {
    public String id;
    public Do doItem;
    public Dare dare;
    public long createdTimestamp;
    public String status; // Completed/Pending/Upcoming
}