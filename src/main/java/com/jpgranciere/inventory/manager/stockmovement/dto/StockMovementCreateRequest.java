package com.jpgranciere.inventory.manager.stockmovement.dto;

import com.jpgranciere.inventory.manager.stockmovement.enums.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record StockMovementCreateRequest(

        @NotNull
        Long productId,

        @Positive
        Integer quantity,

        @NotNull
        MovementType movementType,

        @Size(max = 255)
        String observation
    ) {
}
