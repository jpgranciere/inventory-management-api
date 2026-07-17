package com.jpgranciere.inventory.manager.product.dto;

import com.jpgranciere.inventory.manager.product.entity.Product;
import com.jpgranciere.inventory.manager.product.enums.ProductCategory;
import com.jpgranciere.inventory.manager.product.enums.ProductStatus;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, String sku, ProductCategory category, int stockQuantity, int minStockQuantity, ProductStatus status, BigDecimal price) {
    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getSku(), product.getCategory(), product.getStockQuantity(), product.getMinStockQuantity(),
                product.getStatus(), product.getPrice());
    }
}
