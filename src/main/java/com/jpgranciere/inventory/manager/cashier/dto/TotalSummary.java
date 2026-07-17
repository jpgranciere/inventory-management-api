package com.jpgranciere.inventory.manager.cashier.dto;

import java.math.BigDecimal;

public record TotalSummary(BigDecimal totalSales, BigDecimal totalPix, BigDecimal totalDebit, BigDecimal totalCredit, BigDecimal totalCash) {
}
