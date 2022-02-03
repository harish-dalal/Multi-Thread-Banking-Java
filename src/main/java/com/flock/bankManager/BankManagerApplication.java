package com.flock.bankManager;

import com.flock.bankManager.models.TransactionReq;
import com.flock.bankManager.models.UpdateReq;
import com.flock.bankManager.services.MultiThreadBankService;
import com.flock.bankManager.services.SingleThreadBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankManagerApplication implements CommandLineRunner {

    @Autowired
    private SingleThreadBankService singleThreadBankService;

    @Autowired
    private MultiThreadBankService multiThreadBankService;

    public static void main(String[] args) {
        SpringApplication.run(BankManagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        long start1 = System.nanoTime();
//        for (int i = 0; i < 1000; i++) {
//            singleThreadBankService.depositAmount(new TransactionReq("r@gmail.com", 10));
//        }
//
//        long end1 = System.nanoTime();
//        System.out.println("Elapsed Time in nano seconds: " + (end1 - start1));

        long start2 = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            multiThreadBankService.updateName(new UpdateReq("r@gmail.com", "abc"));
            multiThreadBankService.withdrawAmount(new TransactionReq("r@gmail.com", 100));
            multiThreadBankService.depositAmount(new TransactionReq("r@gmail.com", 200));
        }

        long end2 = System.nanoTime();
        System.out.println("Elapsed Time in nano seconds: " + (end2 - start2));

    }
}
