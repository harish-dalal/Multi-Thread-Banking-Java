package com.flock.bankManager.services;


import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class NotificationService {
    private static ScheduledExecutorService notificationExecutor;
    private static final Integer THRESHOLD = 50;
    private static final Semaphore semaphore = new Semaphore(THRESHOLD );
    private static final ConcurrentLinkedQueue<Integer> notificationsQueue = new ConcurrentLinkedQueue<>();

    private Integer totalNotifications = 0;

    private Integer totalAmount = 0;

    private void sendBatchNotification(){
        semaphore.drainPermits();
        List<Integer> notifications = new ArrayList<>(notificationsQueue);
        notificationsQueue.clear();
        semaphore.release(THRESHOLD);
        totalNotifications += notifications.size();
        System.out.println("timer sending notifications... " + notifications.size());
        notifications.forEach(amount->totalAmount+=amount);
        if(totalNotifications == 2000){
            System.out.println("sent and total amount " + totalAmount);
            notificationExecutor.shutdown();
        }
    }

    public NotificationService(){
        notificationExecutor = Executors.newScheduledThreadPool(1);
        notificationExecutor.scheduleAtFixedRate(this::sendBatchNotification ,0,100, TimeUnit.MILLISECONDS);
    }

    public void newNotification(Integer notification){
        semaphore.acquireUninterruptibly(1);
//        semaphore.drainPermits()
        notificationsQueue.add(notification);
        notificationExecutor.execute(() -> {

            if (notificationsQueue.size() == THRESHOLD) {
                List<Integer> notifications = new ArrayList<>(notificationsQueue);
                notificationsQueue.clear();
                semaphore.release(THRESHOLD);

                totalNotifications += notifications.size();

                notifications.forEach(amount -> totalAmount += amount);

                System.out.println("threshold sending notifications... " + notifications.size());

            }

        });

    }

//    public void newNotification(Integer notification){
//                    notificationExecutor.execute(()->{
//                  notificationsQueue.add(notification)
//                if(notificationsQueue.size() == THRESHOLD){
//                    List<Integer> notifications = new ArrayList<>(notificationsQueue);
//                    totalNotifications += notifications.size();
//
//                    notifications.forEach(amount->totalAmount+=amount);
//
//                    System.out.println("threshold sending notifications... " + notifications.size());
//                    notificationsQueue.clear();
//                }
//            });
//    }

    public Integer getTotalNotifications(){
        return totalNotifications;
    }

    public ScheduledExecutorService getNotificationExecutor(){
        return notificationExecutor;
    }
}
