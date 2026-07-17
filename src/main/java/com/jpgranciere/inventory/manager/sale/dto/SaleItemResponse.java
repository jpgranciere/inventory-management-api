package com.jpgranciere.inventory.manager.sale.dto;

import com.jpgranciere.inventory.manager.sale.entity.SaleItem;

import java.math.BigDecimal;

public record SaleItemResponse(String productName, Integer quantity, BigDecimal unitPrice, BigDecimal subTotal) {
    public SaleItemResponse(SaleItem saleItem){
        this(saleItem.getProduct().getName(), saleItem.getQuantity(), saleItem.getUnitPrice(), saleItem.getSubTotal());
    }
}
