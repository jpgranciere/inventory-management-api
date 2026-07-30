package com.jpgranciere.inventory.manager.cashier.transaction.entity;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.transaction.enums.CashTransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "cash_transaction")
@Table(name = "cash_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CashTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_register_id", nullable = false)
    private CashRegister cashRegister;

    @Enumerated(EnumType.STRING)
    private CashTransactionType type;
    private BigDecimal amount;
    private String reason;
    private LocalDateTime createdAt;
    private String createdBy;

    public CashTransaction(CashRegister cashRegister, CashTransactionType cashTransactionType, BigDecimal amount, String reason) {
        this.cashRegister = cashRegister;
        this.type = cashTransactionType;
        this.amount = amount;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
    }
}
