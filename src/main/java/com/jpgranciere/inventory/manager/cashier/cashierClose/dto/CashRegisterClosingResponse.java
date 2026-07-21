package com.jpgranciere.inventory.manager.cashier.cashierClose.dto;

import com.jpgranciere.inventory.manager.cashier.cashierClose.entity.CashRegisterClosing;
import com.jpgranciere.inventory.manager.cashier.cashierClose.enums.ClosingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CashRegisterClosingResponse(Long id, LocalDate referenceDate, LocalDateTime closedAt, BigDecimal totalPix, BigDecimal totalCash, BigDecimal totalDebit,
                                          BigDecimal totalCredit, BigDecimal totalAmount, int salesCount, ClosingStatus closingStatus) {
    public CashRegisterClosingResponse(CashRegisterClosing registerClosing) {
        this(registerClosing.getId(), registerClosing.getReferenceDate(), registerClosing.getClosedAt(), registerClosing.getTotalPix(),
                registerClosing.getTotalCash(), registerClosing.getTotalDebit(), registerClosing.getTotalCredit(), registerClosing.getTotalAmount(),
                registerClosing.getSalesCount(), registerClosing.getClosingStatus());
    }
}
