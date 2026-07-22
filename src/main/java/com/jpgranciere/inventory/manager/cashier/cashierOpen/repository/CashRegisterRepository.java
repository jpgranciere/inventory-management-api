package com.jpgranciere.inventory.manager.cashier.cashierOpen.repository;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {
    Optional <CashRegister> findByCashRegisterStatus(CashRegisterStatus cashRegisterStatus);

    boolean existsByCashRegisterStatus(CashRegisterStatus cashRegisterStatus);
}
