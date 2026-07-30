package com.jpgranciere.inventory.manager.cashier.transaction.repository;

import com.jpgranciere.inventory.manager.cashier.transaction.entity.CashTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashTransactionRepository extends JpaRepository<CashTransaction, Long> {

}
