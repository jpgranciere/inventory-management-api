package com.jpgranciere.inventory.manager.cashier.cashierOpen.entity;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import com.jpgranciere.inventory.manager.exception.CashRegisterAlreadyClosedException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "CashRegister")
@Table(name = "cash_register")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CashRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CashRegisterStatus cashRegisterStatus;
    private LocalDateTime openedAt;
    private BigDecimal openingBalance;
    private LocalDateTime closedAt;

    public CashRegister(BigDecimal openingBalance) {
        this.cashRegisterStatus = CashRegisterStatus.OPEN;
        this.openedAt = LocalDateTime.now();
        this.openingBalance = openingBalance;
    }

    public void close(){
        if(this.cashRegisterStatus == CashRegisterStatus.CLOSED){
            throw new CashRegisterAlreadyClosedException();
        }

        this.cashRegisterStatus = CashRegisterStatus.CLOSED;
        this.closedAt = LocalDateTime.now();
    }
}
