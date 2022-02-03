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
    private XSync<String> xSync;

    public MultiThreadBankService() {
        priorityBasedExecutorService = new PriorityBasedExecutorService(4, 50);
    }

    public void updateName(UpdateReq updateReq) {
        priorityBasedExecutorService.scheduleJob(new Task(() -> bankRepository.updateName(updateReq), 0));
    }

    public void depositAmount(TransactionReq transactionReq) {
        xSync.execute(transactionReq.getEmail(), () -> {
            priorityBasedExecutorService.scheduleJob(new Task(() -> bankRepository.deposit(transactionReq), 1));
        });
    }

    public void withdrawAmount(TransactionReq transactionReq) {
        xSync.execute(transactionReq.getEmail(), () -> {
            priorityBasedExecutorService.scheduleJob(new Task(() -> bankRepository.withdraw(transactionReq), 1));
        });
    }
}
