package com.flock.bankManager;

import com.flock.bankManager.models.TransactionReq;
import com.flock.bankManager.models.UpdateReq;
import com.flock.bankManager.services.MultiThreadBankService;
import com.flock.bankManager.services.NotificationService;
import com.flock.bankManager.services.SingleThreadBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BankManagerApplication implements CommandLineRunner {

    @Autowired
    private SingleThreadBankService singleThreadBankService;

    @Autowired
    private MultiThreadBankService multiThreadBankService;

    private ExecutorService executorService = Executors.newFixedThreadPool(16);

    @Autowired
    private NotificationService notificationService;

    public static void main(String[] args) {
        SpringApplication.run(BankManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        long start2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(()->multiThreadBankService.withdrawAmount(new TransactionReq("r@gmail.com" , 1)));
            executorService.execute(()->multiThreadBankService.depositAmount(new TransactionReq("r@gmail.com" , -1)));
        }

//        executorService.shutdown();
//        executorService.awaitTermination(10 , TimeUnit.SECONDS);

        notificationService.getNotificationExecutor().awaitTermination(10 , TimeUnit.SECONDS);

        long end2 = System.nanoTime();
        System.out.println("multi Elapsed Time in seconds: " + (end2 - start2) / 1e9);

        long start1 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            singleThreadBankService.withdrawAmount(new TransactionReq("r@gmail.com", 100));
            singleThreadBankService.depositAmount(new TransactionReq("r@gmail.com", 200));
        }

        long end1 = System.nanoTime();
//        System.out.println("single Elapsed Time in seconds: " + (end1 - start1) / 1e9);


    }
}
