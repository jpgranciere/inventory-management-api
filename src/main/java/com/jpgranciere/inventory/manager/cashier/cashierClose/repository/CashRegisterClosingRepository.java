package com.jpgranciere.inventory.manager.cashier.cashierClose.repository;

import com.jpgranciere.inventory.manager.cashier.cashierClose.entity.CashRegisterClosing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;


public interface CashRegisterClosingRepository extends JpaRepository<CashRegisterClosing, Long> {

    boolean existsByReferenceDate(LocalDate referenceDate);
    Optional<CashRegisterClosing> findByReferenceDate(LocalDate referenceDate);
}