package com.jpgranciere.inventory.manager.cashier.cashierClose.entity;

import com.jpgranciere.inventory.manager.cashier.cashierClose.enums.ClosingStatus;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
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
        this.salesCount = salesCount;
    }
}
