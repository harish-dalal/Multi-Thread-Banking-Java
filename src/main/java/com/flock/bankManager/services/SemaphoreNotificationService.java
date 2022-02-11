package com.flock.bankManager.services;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SemaphoreNotificationService {

    private static final int NOTIFICATION_LIMIT = 100;
    private static final Semaphore semaphore = new Semaphore(NOTIFICATION_LIMIT);
    private static final ExecutorService threadPool = Executors.newSingleThreadExecutor();
    private static final ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
    private static final ConcurrentLinkedQueue<Integer> notificationQueue = new ConcurrentLinkedQueue<>();
    private static AtomicInteger totalTransactionAmount = new AtomicInteger(0);

    public SemaphoreNotificationService() {
//        es.scheduleWithFixedDelay(this::flushNotifications, 0, 1, TimeUnit.MINUTES);
    }

    public synchronized void flushNotifications() {
        List<Integer> notifications = new ArrayList<>(notificationQueue);
        int counter = notifications.size();
        threadPool.submit(() -> sendNotifications(notifications));
        while (counter-- > 0) notificationQueue.remove();
        semaphore.release(counter);
    }

    public synchronized void pushNotifications() {
        if (notificationQueue.size() != NOTIFICATION_LIMIT) return;
        List<Integer> notificationsClone = new ArrayList<>(notificationQueue);
        threadPool.submit(() -> {
            sendNotifications(notificationsClone);
        });
        notificationQueue.clear();
        semaphore.release(NOTIFICATION_LIMIT);
    }

    public void sendNotifications(List<Integer> notifications) {
        System.out.println("api call");
        notifications.forEach(amount->totalTransactionAmount.getAndAdd(amount));
        System.out.println(totalTransactionAmount.get());
    }

    public void addNotification(Integer notification) throws InterruptedException {
        if (!semaphore.tryAcquire(1)) {
            pushNotifications();
            if (!semaphore.tryAcquire(1)) throw new RuntimeException("should never happen");
        }
        notificationQueue.add(notification);
    }
}
