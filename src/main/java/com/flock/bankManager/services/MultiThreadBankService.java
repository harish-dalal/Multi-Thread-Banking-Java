package com.flock.bankManager.services;

import com.antkorwin.xsync.XSync;
import com.flock.bankManager.models.Task;
import com.flock.bankManager.models.TransactionReq;
import com.flock.bankManager.models.UpdateReq;
import com.flock.bankManager.repositories.BankRepository;
import com.flock.bankManager.utils.PriorityBasedExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiThreadBankService {
    private final PriorityBasedExecutorService priorityBasedExecutorService;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SemaphoreNotificationService semaphoreNotificationService;


    public MultiThreadBankService() {
        priorityBasedExecutorService = new PriorityBasedExecutorService(1, 1);
    }

    public void updateName(UpdateReq updateReq) {
        bankRepository.updateName(updateReq);
//        notificationService.newNotification(updateReq.getEmail().toString() , "update");
    }

    public void depositAmount(TransactionReq transactionReq) {
        bankRepository.deposit(transactionReq);
        notificationService.newNotification(transactionReq.getAmount());
//        try {
//            semaphoreNotificationService.addNotification(transactionReq.getAmount());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void withdrawAmount(TransactionReq transactionReq) {
        bankRepository.withdraw(transactionReq);
        notificationService.newNotification(transactionReq.getAmount());
//        try {
//            semaphoreNotificationService.addNotification(transactionReq.getAmount());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
