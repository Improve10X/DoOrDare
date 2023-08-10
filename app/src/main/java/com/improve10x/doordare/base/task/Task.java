package com.improve10x.doordare.base.task;

import java.io.Serializable;

public class Task implements Serializable {
    private String id;
    private Do doItem;
    private Dare dare;
    private long createdTimestamp;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Do getDoItem() {
        return doItem;
    }

    public void setDoItem(Do doItem) {
        this.doItem = doItem;
    }

    public Dare getDare() {
        return dare;
    }

    public void setDare(Dare dare) {
        this.dare = dare;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}