package com.jpgranciere.inventory.manager.product.dto;

import com.jpgranciere.inventory.manager.product.enums.ProductCategory;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record ProductUpdateRequest(

        String name,

        String description,

        String sku,

        ProductCategory category,

        @Min(10)
        Integer minStockQuantity,

        BigDecimal price


    ) {
}
