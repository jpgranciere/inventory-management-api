package com.jpgranciere.inventory.manager.cashier.repository;

import com.jpgranciere.inventory.manager.cashier.entity.CashRegisterClosing;
import com.jpgranciere.inventory.manager.cashier.enums.ClosingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;


public interface CashRegisterClosingRepository extends JpaRepository<CashRegisterClosing, Long> {

    boolean existsByReferenceDateAndClosingStatus(LocalDate data, ClosingStatus closingStatus);

    CashRegisterClosing getByReferenceDate(LocalDate request);
    Optional<CashRegisterClosing> findByReferenceDate(LocalDate referenceDate);
}