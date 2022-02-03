package com.flock.bankManager.models;

public class Task {
    private Runnable task;
    private int taskPriority;

    public Task(Runnable task, int taskPriority) {
        this.task = task;
        this.taskPriority = taskPriority;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }
}
