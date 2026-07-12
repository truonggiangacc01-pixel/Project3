package com.horseracing.project3.service;

import com.horseracing.project3.dto.response.WalletResponse;
import com.horseracing.project3.entity.PaymentTransaction;
import com.horseracing.project3.entity.Spectator;
import com.horseracing.project3.enums.PaymentGateway;
import com.horseracing.project3.enums.TransactionStatus;
import com.horseracing.project3.enums.TransactionType;
import com.horseracing.project3.repository.PaymentTransactionRepo;
import com.horseracing.project3.repository.SpectatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private SpectatorRepo spectatorRepo;

    @Autowired
    private PaymentTransactionRepo transactionRepo;

    public WalletResponse getWallet(Integer spectatorId) {
        Spectator spectator = findSpectator(spectatorId);
        return new WalletResponse(spectator.getId(), spectator.getFullName(), spectator.getWalletBalance());
    }

    public List<PaymentTransaction> getTransactions(Integer spectatorId) {
        findSpectator(spectatorId);
        return transactionRepo.findBySpectatorIdOrderByTransactionDateDesc(spectatorId);
    }

    @Transactional
    public WalletResponse deposit(Integer spectatorId, BigDecimal amount, PaymentGateway gateway) {
        validateAmount(amount);
        Spectator spectator = findSpectator(spectatorId);
        spectator.setWalletBalance(spectator.getWalletBalance().add(amount));
        spectatorRepo.save(spectator);
        saveTransaction(spectator, amount, gateway, TransactionStatus.SUCCESS, TransactionType.WALLET_DEPOSIT);
        return new WalletResponse(spectator.getId(), spectator.getFullName(), spectator.getWalletBalance());
    }

    @Transactional
    public WalletResponse withdraw(Integer spectatorId, BigDecimal amount, PaymentGateway gateway) {
        validateAmount(amount);
        Spectator spectator = findSpectator(spectatorId);
        if (spectator.getWalletBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }
        spectator.setWalletBalance(spectator.getWalletBalance().subtract(amount));
        spectatorRepo.save(spectator);
        saveTransaction(spectator, amount, gateway, TransactionStatus.SUCCESS, TransactionType.WALLET_WITHDRAWAL);
        return new WalletResponse(spectator.getId(), spectator.getFullName(), spectator.getWalletBalance());
    }

    @Transactional
    public void debitForPrediction(Spectator spectator, BigDecimal amount) {
        validateAmount(amount);
        if (spectator.getWalletBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }
        spectator.setWalletBalance(spectator.getWalletBalance().subtract(amount));
        spectatorRepo.save(spectator);
        saveTransaction(spectator, amount, PaymentGateway.MOMO, TransactionStatus.SUCCESS, TransactionType.PREDICTION_STAKE);
    }

    @Transactional
    public void creditPredictionPayout(Spectator spectator, BigDecimal amount) {
        validateAmount(amount);
        spectator.setWalletBalance(spectator.getWalletBalance().add(amount));
        spectatorRepo.save(spectator);
        saveTransaction(spectator, amount, PaymentGateway.MOMO, TransactionStatus.SUCCESS, TransactionType.PREDICTION_PAYOUT);
    }

    private Spectator findSpectator(Integer spectatorId) {
        return spectatorRepo.findById(spectatorId)
                .orElseThrow(() -> new RuntimeException("Spectator not found"));
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }
    }

    private void saveTransaction(Spectator spectator, BigDecimal amount, PaymentGateway gateway, TransactionStatus status, TransactionType type) {
        PaymentGateway selectedGateway = gateway == null ? PaymentGateway.MOMO : gateway;
        transactionRepo.save(new PaymentTransaction(spectator, null, amount, status, selectedGateway, type));
    }
}
