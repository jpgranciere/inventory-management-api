package com.jpgranciere.inventory.manager.cashier.cashierClose.service;

import com.jpgranciere.inventory.manager.cashier.cashierClose.dto.CashRegisterClosingResponse;
import com.jpgranciere.inventory.manager.cashier.cashierClose.dto.TotalSummary;
import com.jpgranciere.inventory.manager.cashier.cashierClose.entity.CashRegisterClosing;
import com.jpgranciere.inventory.manager.cashier.cashierClose.repository.CashRegisterClosingRepository;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.repository.CashRegisterRepository;
import com.jpgranciere.inventory.manager.cashier.transaction.entity.CashTransaction;
import com.jpgranciere.inventory.manager.cashier.transaction.enums.CashTransactionType;
import com.jpgranciere.inventory.manager.cashier.transaction.repository.CashTransactionRepository;
import com.jpgranciere.inventory.manager.exception.*;
import com.jpgranciere.inventory.manager.sale.entity.Sale;
import com.jpgranciere.inventory.manager.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CashRegisterClosingService {
    private final CashRegisterClosingRepository cashRegisterClosingRepository;
    private final SaleRepository saleRepository;
    private final CashRegisterRepository cashRegisterRepository;
    private final CashTransactionRepository cashTransactionRepository;

    @Transactional
    public CashRegisterClosingResponse registerCashClosing(){
        CashRegister cashRegister = cashRegisterRepository.findByCashRegisterStatus(CashRegisterStatus.OPEN)
                .orElseThrow(CashRegisterNotOpenException::new);

        List<Sale> sales = saleRepository.findByCashRegisterId(cashRegister.getId());

        List<CashTransaction> transactions = cashTransactionRepository.findByCashRegisterIdOrderByCreatedAtAsc(cashRegister.getId());

        BigDecimal supplies = BigDecimal.ZERO;
        BigDecimal withdrawals = BigDecimal.ZERO;

        for (CashTransaction cashTransaction : transactions){
            switch (cashTransaction.getType()){
                case SUPPLY -> supplies = supplies.add(cashTransaction.getAmount());
                case WITHDRAWAL -> withdrawals = withdrawals.add(cashTransaction.getAmount());
            }
        }

        TotalSummary totals = calculateClosingResponse(sales);
        BigDecimal expectedCashBalance = cashRegister.getOpeningBalance().add(totals.totalCash()).add(supplies).subtract(withdrawals);

        CashRegisterClosing cashRegisterClosing = new CashRegisterClosing(
                cashRegister.getOpenedAt().toLocalDate(),
                totals.totalSales(),
                totals.totalPix(),
                totals.totalDebit(),
                totals.totalCredit(),
                totals.totalCash(),
                cashRegister.getOpeningBalance(),
                supplies,
                withdrawals,
                expectedCashBalance,
                sales.size(),
                cashRegister);

        cashRegister.close();

        CashRegisterClosing savedClosing = cashRegisterClosingRepository.save(cashRegisterClosing);
        log.info("Caixa fechado: cashRegisterId={}, closingId={}, totalVendas={}",
                cashRegister.getId(), savedClosing.getId(), totals.totalSales());

        return new CashRegisterClosingResponse(savedClosing);
    }

    public Page<CashRegisterClosingResponse> listClosedRegisters(Pageable pageable){
        return cashRegisterClosingRepository.findAll(pageable)
                .map(CashRegisterClosingResponse::new);
    }

    public CashRegisterClosingResponse getClosingByDate(LocalDate date){
        CashRegisterClosing closing = cashRegisterClosingRepository.findByReferenceDate(date)
                .orElseThrow(SalesNotFoundException::new);

        return new CashRegisterClosingResponse(closing);
    }

    //methods

    private TotalSummary calculateClosingResponse(List<Sale> sales){
        BigDecimal totalPix = BigDecimal.ZERO;
        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;
        BigDecimal totalCash = BigDecimal.ZERO;

        for(Sale item : sales){
            switch (item.getPaymentMethod()){
                case PIX -> totalPix = totalPix.add(item.getTotal());
                case DEBIT_CARD -> totalDebit = totalDebit.add(item.getTotal());
                case CREDIT_CARD -> totalCredit = totalCredit.add(item.getTotal());
                case CASH -> totalCash = totalCash.add(item.getTotal());
            }
        }

        BigDecimal totalSales = totalPix.add(totalDebit).add(totalCredit).add(totalCash);

        return new TotalSummary(totalSales, totalPix, totalDebit, totalCredit, totalCash);
    }

}
