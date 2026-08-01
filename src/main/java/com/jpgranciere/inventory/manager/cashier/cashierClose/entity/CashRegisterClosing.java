package com.jpgranciere.inventory.manager.cashier.cashierClose.entity;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "CashRegisterClosing")
@Table(name = "cash_register_closing" )
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CashRegisterClosing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime closedAt;
    private LocalDate referenceDate = LocalDate.now();
    private BigDecimal totalPix = BigDecimal.ZERO;
    private BigDecimal totalCash = BigDecimal.ZERO;
    private BigDecimal totalDebit = BigDecimal.ZERO;
    private BigDecimal totalCredit = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal openingBalance = BigDecimal.ZERO;
    private BigDecimal supplies = BigDecimal.ZERO;
    private BigDecimal withdrawals = BigDecimal.ZERO;
    private BigDecimal expectedCashBalance = BigDecimal.ZERO;
    private int salesCount = 0;

    @OneToOne
    @JoinColumn(name = "cash_register_id", nullable = false, unique = true)
    private CashRegister cashRegister;


    public CashRegisterClosing(
            LocalDate request,
            BigDecimal totalSales,
            BigDecimal totalPix,
            BigDecimal totalDebit,
            BigDecimal totalCredit,
            BigDecimal totalCash,
            BigDecimal openingBalance,
            BigDecimal supplies,
            BigDecimal withdrawals,
            BigDecimal expectedCashBalance,
            int salesCount,
            CashRegister cashRegister)
    {
        this.cashRegister = cashRegister;
        this.closedAt = LocalDateTime.now();
        this.referenceDate = request;
        this.totalPix = totalPix;
        this.totalCash = totalCash;
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
        this.totalAmount = totalSales;
        this.openingBalance = openingBalance;
        this.supplies = supplies;
        this.withdrawals = withdrawals;
        this.expectedCashBalance = expectedCashBalance;
        this.salesCount = salesCount;
    }
}
