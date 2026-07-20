package com.jpgranciere.inventory.manager.product.dto;

import com.jpgranciere.inventory.manager.product.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;

public record ProductStatusUpdateRequest(

        @NotNull
        ProductStatus status
    ) {
}
