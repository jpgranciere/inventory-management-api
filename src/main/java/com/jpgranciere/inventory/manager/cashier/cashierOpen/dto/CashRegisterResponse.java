package com.jpgranciere.inventory.manager.cashier.cashierOpen.dto;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CashRegisterResponse(Long id, CashRegisterStatus cashRegisterStatus, LocalDateTime openedAt, BigDecimal openingBalance, LocalDateTime closedAt) {
    public CashRegisterResponse(CashRegister cashRegister) {
        this(cashRegister.getId(), cashRegister.getCashRegisterStatus(), cashRegister.getOpenedAt(), cashRegister.getOpeningBalance(), cashRegister.getClosedAt());
    }
}
