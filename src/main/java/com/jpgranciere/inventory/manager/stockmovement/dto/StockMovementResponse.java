package com.jpgranciere.inventory.manager.stockmovement.dto;

import com.jpgranciere.inventory.manager.stockmovement.entity.StockMovement;
import com.jpgranciere.inventory.manager.stockmovement.enums.MovementType;

import java.time.LocalDateTime;

public record StockMovementResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        MovementType movementType,
        String observation,
        LocalDateTime movementDate
    ) {

    public StockMovementResponse(StockMovement stockMovement){
        this(
                stockMovement.getId(),
                stockMovement.getProduct().getId(),
                stockMovement.getProduct().getName(),
                stockMovement.getQuantity(),
                stockMovement.getMovementType(),
                stockMovement.getObservation(),
                stockMovement.getCreatedAt()
        );
    }
}
