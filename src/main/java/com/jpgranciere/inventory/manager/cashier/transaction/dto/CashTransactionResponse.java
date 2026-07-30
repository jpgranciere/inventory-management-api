package com.jpgranciere.inventory.manager.cashier.transaction.dto;

import com.jpgranciere.inventory.manager.cashier.transaction.entity.CashTransaction;
import com.jpgranciere.inventory.manager.cashier.transaction.enums.CashTransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CashTransactionResponse(Long id, Long cashRegisterId, CashTransactionType type, BigDecimal amount, String reason, LocalDateTime createdAt){

    public CashTransactionResponse(CashTransaction cashTransaction) {
        this(cashTransaction.getId(), cashTransaction.getCashRegister().getId(), cashTransaction.getType(),
                cashTransaction.getAmount(), cashTransaction.getReason(), cashTransaction.getCreatedAt());
    }
}
