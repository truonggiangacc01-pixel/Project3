package com.horseracing.project3.repository;

import com.horseracing.project3.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTransactionRepo extends JpaRepository<PaymentTransaction, Integer> {
    List<PaymentTransaction> findBySpectatorIdOrderByTransactionDateDesc(Integer spectatorId);
    List<PaymentTransaction> findBySpectatorIdAndTransactionTypeOrderByTransactionDateDesc(Integer spectatorId, com.horseracing.project3.enums.TransactionType transactionType);
}
