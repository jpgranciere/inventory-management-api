package com.jpgranciere.inventory.manager.cashier.transaction.service;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.repository.CashRegisterRepository;
import com.jpgranciere.inventory.manager.cashier.transaction.dto.CashTransactionCreateRequest;
import com.jpgranciere.inventory.manager.cashier.transaction.dto.CashTransactionResponse;
import com.jpgranciere.inventory.manager.cashier.transaction.entity.CashTransaction;
import com.jpgranciere.inventory.manager.cashier.transaction.enums.CashTransactionType;
import com.jpgranciere.inventory.manager.cashier.transaction.repository.CashTransactionRepository;
import com.jpgranciere.inventory.manager.exception.CashRegisterNotOpenException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CashTransactionService {
    private final CashTransactionRepository cashTransactionRepository;
    private final CashRegisterRepository cashRegisterRepository;

    public CashTransactionResponse create(CashTransactionCreateRequest request){
        var cashRegister = cashRegisterRepository.findByCashRegisterStatus(CashRegisterStatus.OPEN)
                .orElseThrow(CashRegisterNotOpenException::new);

        CashTransaction cashTransaction = new CashTransaction(
                cashRegister,
                request.cashTransactionType(),
                request.amount(),
                request.reason()
        );

        var savedTransaction = cashTransactionRepository.save(cashTransaction);

        return new CashTransactionResponse(savedTransaction);
    }
}
