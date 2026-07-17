package com.jpgranciere.inventory.manager.cashier.entity;

import com.jpgranciere.inventory.manager.cashier.enums.ClosingStatus;
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

    private LocalDateTime closedAt = LocalDateTime.now();

    @PastOrPresent(message = "Data não pode ser futura")
    private LocalDate referenceDate = LocalDate.now();

    private BigDecimal totalPix = BigDecimal.ZERO;
    private BigDecimal totalCash = BigDecimal.ZERO;
    private BigDecimal totalDebit = BigDecimal.ZERO;
    private BigDecimal totalCredit = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private int salesCount = 0;

    @Enumerated(value = EnumType.STRING)
    private ClosingStatus closingStatus;

    public CashRegisterClosing(LocalDate request, BigDecimal totalSales, BigDecimal totalPix, BigDecimal totalDebit, BigDecimal totalCredit, BigDecimal totalCash, int salesCount) {
        this.closedAt = LocalDateTime.now();
        this.referenceDate = request;
        this.totalPix = totalPix;
        this.totalCash = totalCash;
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
        this.totalAmount = totalSales;
        this.closingStatus = ClosingStatus.CLOSED;
        this.salesCount = salesCount;
    }
}
