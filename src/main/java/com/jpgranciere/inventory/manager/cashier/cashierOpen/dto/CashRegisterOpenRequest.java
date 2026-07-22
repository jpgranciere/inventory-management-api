package com.jpgranciere.inventory.manager.cashier.cashierOpen.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CashRegisterOpenRequest(
        @NotNull
        @PositiveOrZero
        BigDecimal openingBalance)
    {
}
