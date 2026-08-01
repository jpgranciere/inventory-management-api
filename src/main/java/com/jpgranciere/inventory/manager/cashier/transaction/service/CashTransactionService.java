package com.jpgranciere.inventory.manager.cashier.transaction.service;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.repository.CashRegisterRepository;
import com.jpgranciere.inventory.manager.cashier.transaction.dto.CashTransactionCreateRequest;
import com.jpgranciere.inventory.manager.cashier.transaction.dto.CashTransactionResponse;
import com.jpgranciere.inventory.manager.cashier.transaction.entity.CashTransaction;
import com.jpgranciere.inventory.manager.cashier.transaction.enums.CashTransactionType;
import com.jpgranciere.inventory.manager.cashier.transaction.repository.CashTransactionRepository;
import com.jpgranciere.inventory.manager.exception.CashRegisterNotOpenException;
import com.jpgranciere.inventory.manager.exception.InsufficientCashBalanceException;
import com.jpgranciere.inventory.manager.exception.SalesNotFoundException;
import com.jpgranciere.inventory.manager.exception.StockMovementNotFoundException;
import com.jpgranciere.inventory.manager.sale.entity.Sale;
import com.jpgranciere.inventory.manager.sale.enums.PaymentMethod;
import com.jpgranciere.inventory.manager.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashTransactionService {
    private final CashTransactionRepository cashTransactionRepository;
    private final CashRegisterRepository cashRegisterRepository;
    private final SaleRepository saleRepository;

    @Transactional
    public CashTransactionResponse create(CashTransactionCreateRequest request){
        var cashRegister = cashRegisterRepository.findByCashRegisterStatus(CashRegisterStatus.OPEN)
                .orElseThrow(CashRegisterNotOpenException::new);

        BigDecimal balance = calculateBalance(cashRegister);
        if(request.cashTransactionType() == CashTransactionType.WITHDRAWAL && request.amount().compareTo(balance) > 0){
            throw new InsufficientCashBalanceException();
        }

        CashTransaction cashTransaction = new CashTransaction(cashRegister, request.cashTransactionType(), request.amount(), request.reason());

        var savedTransaction = cashTransactionRepository.save(cashTransaction);

        return new CashTransactionResponse(savedTransaction);
    }

    private BigDecimal calculateBalance(CashRegister cashRegister){
        BigDecimal balance = cashRegister.getOpeningBalance();

        List<Sale> sales = saleRepository.findByCashRegisterId(cashRegister.getId());

        List<CashTransaction> transactions = cashTransactionRepository.findByCashRegisterIdOrderByCreatedAtAsc(cashRegister.getId());

        for (Sale sale : sales){
            if(sale.getPaymentMethod() == PaymentMethod.CASH){
                balance = balance.add(sale.getTotal());
            }
        }

        for (CashTransaction transaction : transactions){
            if(transaction.getType() == CashTransactionType.SUPPLY){
                balance = balance.add(transaction.getAmount());
            }
            if(transaction.getType() == CashTransactionType.WITHDRAWAL){
                balance = balance.subtract(transaction.getAmount());
            }
        }

        return balance;
    }
}
