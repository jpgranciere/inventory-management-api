package com.jpgranciere.inventory.manager.product.dto;

import com.jpgranciere.inventory.manager.product.enums.ProductCategory;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.EAN;

import java.math.BigDecimal;

public record ProductCreateRequest(

        @NotBlank(message = "Nome é obrigatorio")
        String name,

        @NotBlank(message = "Nome é obrigatorio")
        String description,

        @NotBlank(message = "SKU é obrigatorio")
        String sku,

        @NotNull(message = "Categoria é obrigatorio")
        ProductCategory category,

        @Min(10)
        int minStockQuantity,

        @NotNull(message = "Preço é obrigatorio")
        @Positive
        BigDecimal price,

        @NotBlank(message = "Código de barras é obrigatorio")
        @EAN
        String gtin
) {
}
