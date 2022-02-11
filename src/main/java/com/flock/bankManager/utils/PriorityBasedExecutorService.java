package com.flock.bankManager.utils;

import com.flock.bankManager.models.Task;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBasedExecutorService {

    private final ExecutorService priorityJobPoolExecutor;
    private final PriorityBlockingQueue<Task> priorityQueue;
    private ExecutorService priorityJobScheduler
            = Executors.newSingleThreadExecutor();

    public PriorityBasedExecutorService(Integer poolSize, Integer queueSize) {
        priorityJobPoolExecutor = Executors.newFixedThreadPool(poolSize);
        priorityQueue = new PriorityBlockingQueue<Task>(
                queueSize,
                Comparator.comparing(Task::getTaskPriority, Comparator.reverseOrder()));
        priorityJobScheduler.execute(() -> {
            while (true) {
                try {
                    priorityJobPoolExecutor.execute(priorityQueue.take().getTask());
                } catch (InterruptedException e) {
                    // exception needs special handling
                    break;
                }
            }
        });
    }


    public void scheduleJob(Task task) {
        priorityQueue.add(task);
    }
}
