package com.jpgranciere.inventory.manager.cashier.summary.service;

import com.jpgranciere.inventory.manager.cashier.cashierClose.dto.TotalSummary;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.repository.CashRegisterRepository;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.service.CashRegisterService;
import com.jpgranciere.inventory.manager.cashier.summary.dto.CashRegisterSummaryResponse;
import com.jpgranciere.inventory.manager.cashier.transaction.entity.CashTransaction;
import com.jpgranciere.inventory.manager.cashier.transaction.enums.CashTransactionType;
import com.jpgranciere.inventory.manager.cashier.transaction.repository.CashTransactionRepository;
import com.jpgranciere.inventory.manager.exception.CashRegisterAlreadyClosedException;
import com.jpgranciere.inventory.manager.exception.CashRegisterNotOpenException;
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
public class CashRegisterSummaryService {
    private final CashRegisterRepository cashRegisterRepository;
    private final SaleRepository saleRepository;
    private final CashTransactionRepository cashTransactionRepository;

    @Transactional
    public CashRegisterSummaryResponse getCurrentSummary(){
        var cashOpen = cashRegisterRepository.findByCashRegisterStatus(CashRegisterStatus.OPEN)
                .orElseThrow(CashRegisterNotOpenException::new);

        List<Sale> sales = saleRepository.findByCashRegisterId(cashOpen.getId());

        List<CashTransaction> transactions = cashTransactionRepository.findByCashRegisterIdOrderByCreatedAtAsc(cashOpen.getId());


        BigDecimal supplies = BigDecimal.ZERO;
        BigDecimal withdrawals = BigDecimal.ZERO;
        BigDecimal cashSales = BigDecimal.ZERO;
        BigDecimal pixSales = BigDecimal.ZERO;
        BigDecimal creditSales = BigDecimal.ZERO;
        BigDecimal debitSales = BigDecimal.ZERO;

        for (CashTransaction cashTransaction : transactions){
            switch (cashTransaction.getType()){
                case SUPPLY -> supplies = supplies.add(cashTransaction.getAmount());
                case WITHDRAWAL -> withdrawals = withdrawals.add(cashTransaction.getAmount());
            }
        }

        for (Sale sale : sales){
            switch (sale.getPaymentMethod()){
                case CASH -> cashSales = cashSales.add(sale.getTotal());
                case PIX -> pixSales = pixSales.add(sale.getTotal());
                case CREDIT_CARD -> creditSales = creditSales.add(sale.getTotal());
                case DEBIT_CARD -> debitSales = debitSales.add(sale.getTotal());
            }
        }

        int salesCount = sales.size();
        BigDecimal totalSales = cashSales.add(pixSales).add(creditSales).add(debitSales);
        BigDecimal expectedCashBalance = cashOpen.getOpeningBalance().add(cashSales).add(supplies).subtract(withdrawals);

        return new CashRegisterSummaryResponse(cashOpen.getId(),
                cashOpen.getOpeningBalance(),
                cashSales,
                supplies,
                withdrawals,
                expectedCashBalance,
                pixSales,
                debitSales,
                creditSales,
                totalSales,
                salesCount);
    }
}
