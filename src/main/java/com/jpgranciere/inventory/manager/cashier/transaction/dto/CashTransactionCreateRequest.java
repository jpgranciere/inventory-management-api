package com.jpgranciere.inventory.manager.cashier.transaction.dto;

import com.jpgranciere.inventory.manager.cashier.transaction.enums.CashTransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CashTransactionCreateRequest(

        @NotNull
        CashTransactionType cashTransactionType,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        @NotBlank
        @Size(max = 255)
        String reason
    ) {
}
