package com.jpgranciere.inventory.manager.cashier.summary.dto;

import java.math.BigDecimal;

public record CashRegisterSummaryResponse(Long cashRegisterId,
                                          BigDecimal openingBalance,
                                          BigDecimal cashSales,
                                          BigDecimal supplies,
                                          BigDecimal withdrawals,
                                          BigDecimal expectedCashBalance,
                                          BigDecimal pixSales,
                                          BigDecimal debitSales,
                                          BigDecimal creditSales,
                                          BigDecimal totalSales,
                                          int salesCount) {
}
