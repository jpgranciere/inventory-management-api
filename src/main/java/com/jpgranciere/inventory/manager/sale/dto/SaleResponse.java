package com.jpgranciere.inventory.manager.sale.dto;

import com.jpgranciere.inventory.manager.sale.entity.Sale;
import com.jpgranciere.inventory.manager.sale.enums.PaymentMethod;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponse(Long id, LocalDateTime createdAt, BigDecimal total, List<SaleItemResponse> items, PaymentMethod paymentMethod, BigDecimal amountPaid, BigDecimal changeAmount) {

    public SaleResponse(Sale sale) {
        this(sale.getId(), sale.getCreatedAt(), sale.getTotal(), sale.getSaleItemList().stream().map(SaleItemResponse::new).toList(), sale.getPaymentMethod(), sale.getAmountPaid(), sale.getChangeAmount());
    }
}
