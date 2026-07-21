package com.jpgranciere.inventory.manager.cashier.cashierClose.repository;

import com.jpgranciere.inventory.manager.cashier.cashierClose.entity.CashRegisterClosing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;


public interface CashRegisterClosingRepository extends JpaRepository<CashRegisterClosing, Long> {

//    boolean existsByReferenceDateAndClosingStatus(LocalDate data, ClosingStatus closingStatus);
    boolean existsByReferenceDate(LocalDate referenceDate);

//    CashRegisterClosing getByReferenceDate(LocalDate request);
    Optional<CashRegisterClosing> findByReferenceDate(LocalDate referenceDate);
}