package com.jpgranciere.inventory.manager.sale.dto;

import com.jpgranciere.inventory.manager.sale.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record SaleCreateRequest(

        @NotNull
        List<@Valid SaleItemRequest> items,

        @NotNull
        PaymentMethod paymentMethod,

        @NotNull
        BigDecimal amountPaid) {
}
