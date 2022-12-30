package com.improve10x.doordare.pastfragment;

public class PastTask {

    public String id;
    public String time;
    public String date;
    public String monthAndYear;
    public String task;
    public String dare;
    public String reducedTime;

    public PastTask() {
    }

    public PastTask(String id, String task, String dare, String time, String date, String monthAndYear, String reducedTime) {
        this.id = id;
        this.task = task;
        this.dare = dare;
        this.time = time;
        this.date = date;
        this.monthAndYear = monthAndYear;
        this.reducedTime = reducedTime;
    }
}
