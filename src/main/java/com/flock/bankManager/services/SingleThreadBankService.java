package com.flock.bankManager.services;

import com.flock.bankManager.models.TransactionReq;
import com.flock.bankManager.models.UpdateReq;
import com.flock.bankManager.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleThreadBankService {

    @Autowired
    private BankRepository bankRepository;

    public void updateName(UpdateReq updateReq) {
        bankRepository.updateName(updateReq);
    }

    public void depositAmount(TransactionReq transactionReq) {
        bankRepository.deposit(transactionReq);
    }

    public void withdrawAmount(TransactionReq transactionReq) {
        bankRepository.withdraw(transactionReq);
    }
}
