package com.horseracing.project3.entity;

import com.horseracing.project3.enums.PaymentGateway;
import com.horseracing.project3.enums.TransactionStatus;
import com.horseracing.project3.enums.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PaymentTransaction")
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "SpectatorId", nullable = false)
    private Spectator spectator;

    @ManyToOne
    @JoinColumn(name = "TicketId")
    private Ticket ticket;

    @Column(name = "amount", columnDefinition = "DECIMAL(15, 0)", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_gateway", nullable = false)
    private PaymentGateway paymentGateway;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType = TransactionType.TICKET_PURCHASE;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    public PaymentTransaction() {
    }

    public PaymentTransaction(Spectator spectator, Ticket ticket, BigDecimal amount, TransactionStatus status, PaymentGateway paymentGateway) {
        this.spectator = spectator;
        this.ticket = ticket;
        this.amount = amount;
        this.status = status;
        this.paymentGateway = paymentGateway;
        this.transactionType = TransactionType.TICKET_PURCHASE;
        this.transactionDate = LocalDateTime.now();
    }

    public PaymentTransaction(Spectator spectator, Ticket ticket, BigDecimal amount, TransactionStatus status, PaymentGateway paymentGateway, TransactionType transactionType) {
        this.spectator = spectator;
        this.ticket = ticket;
        this.amount = amount;
        this.status = status;
        this.paymentGateway = paymentGateway;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public Spectator getSpectator() {
        return spectator;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
