package com.jpgranciere.inventory.manager.cashier.transaction.repository;

import com.jpgranciere.inventory.manager.cashier.transaction.entity.CashTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CashTransactionRepository extends JpaRepository<CashTransaction, Long> {
    List<CashTransaction> findByCashRegisterIdOrderByCreatedAtAsc(Long cashRegisterId);
}
