package com.jpgranciere.inventory.manager.cashier.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CashRegisterClosingCreateRequest(
        @NotNull
        LocalDate referenceDate
    ) {
}
