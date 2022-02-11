package com.flock.bankManager.repositories;

import com.flock.bankManager.models.TransactionReq;
import com.flock.bankManager.models.UpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicInteger;

import static com.flock.bankManager.utils.DBConstants.Queries.*;
import static com.flock.bankManager.utils.DBConstants.Variables.*;

@Repository
public class BankRepository {
    private final AtomicInteger updateCount = new AtomicInteger(0);
    private final AtomicInteger transactionCount = new AtomicInteger(0);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void updateName(UpdateReq updateReq) {
        MapSqlParameterSource params = new MapSqlParameterSource(SQL_EMAIL, updateReq.getEmail());
        params.addValue(SQL_NAME, updateReq.getName());
        jdbcTemplate.update(SQL_UPDATE_NAME, params);

        System.out.println("Name Updated: " + updateCount.incrementAndGet());
    }

    public void deposit(TransactionReq transactionReq) {
        MapSqlParameterSource params = new MapSqlParameterSource(SQL_EMAIL, transactionReq.getEmail());
        params.addValue(SQL_AMOUNT, transactionReq.getAmount());
        jdbcTemplate.update(SQL_DEPOSIT_AMT, params);

//        System.out.println("Money Deposited: " + transactionCount.incrementAndGet());
    }

    public void withdraw(TransactionReq transactionReq) {
        MapSqlParameterSource params = new MapSqlParameterSource(SQL_EMAIL, transactionReq.getEmail());
        params.addValue(SQL_AMOUNT, transactionReq.getAmount());
        jdbcTemplate.update(SQL_WITHDRAW_AMT, params);

//        System.out.println("Money Withdrew: " + transactionCount.incrementAndGet());
    }
}
