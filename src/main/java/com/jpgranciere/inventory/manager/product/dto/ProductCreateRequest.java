package com.jpgranciere.inventory.manager.product.dto;

import com.jpgranciere.inventory.manager.product.enums.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductCreateRequest(

        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        String sku,
        @NotNull
        ProductCategory category,
        @Min(10)
        int minStockQuantity,
        @NotNull
        BigDecimal price,
        @NotBlank
        @Min(8)
        String gtin
) {
}
