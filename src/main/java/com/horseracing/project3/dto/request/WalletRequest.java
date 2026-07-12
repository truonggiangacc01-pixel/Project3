package com.horseracing.project3.dto.request;

import com.horseracing.project3.enums.PaymentGateway;

import java.math.BigDecimal;

public class WalletRequest {
    private BigDecimal amount;
    private PaymentGateway gateway;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public void setGateway(PaymentGateway gateway) {
        this.gateway = gateway;
    }
}
