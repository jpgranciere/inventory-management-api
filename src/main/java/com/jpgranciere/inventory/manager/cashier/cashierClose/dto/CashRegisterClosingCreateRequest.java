package com.jpgranciere.inventory.manager.cashier.cashierClose.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record CashRegisterClosingCreateRequest(
        @NotNull
        @PastOrPresent(message = "Data não pode ser futura")
        LocalDate referenceDate
    ) {
}
