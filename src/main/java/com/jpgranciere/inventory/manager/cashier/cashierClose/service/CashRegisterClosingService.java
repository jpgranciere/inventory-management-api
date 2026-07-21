package com.jpgranciere.inventory.manager.cashier.cashierClose.service;

import com.jpgranciere.inventory.manager.cashier.cashierClose.dto.CashRegisterClosingResponse;
import com.jpgranciere.inventory.manager.cashier.cashierClose.dto.TotalSummary;
import com.jpgranciere.inventory.manager.cashier.cashierClose.entity.CashRegisterClosing;
import com.jpgranciere.inventory.manager.cashier.cashierClose.repository.CashRegisterClosingRepository;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.repository.CashRegisterRepository;
import com.jpgranciere.inventory.manager.exception.*;
import com.jpgranciere.inventory.manager.sale.entity.Sale;
import com.jpgranciere.inventory.manager.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CashRegisterClosingService {
    private final CashRegisterClosingRepository cashRegisterClosingRepository;
    private final SaleRepository saleRepository;
    private final CashRegisterRepository cashRegisterRepository;

    @Transactional
    public CashRegisterClosingResponse registerCashClosing(Long id){
        CashRegister cashRegister = cashRegisterRepository.findByCashRegisterStatus(CashRegisterStatus.OPEN)
                .orElseThrow(CashRegisterNotOpenException::new);

        List<Sale> sales = saleRepository.findByCashRegisterId(cashRegister.getId());

        if(sales.isEmpty()){
            throw new SalesNotFound();
        }

        TotalSummary totals = calculateClosingResponse(sales);

        CashRegisterClosing cashRegisterClosing = new CashRegisterClosing(
                cashRegister.getOpenedAt().toLocalDate(),
                totals.totalSales(),
                totals.totalPix(),
                totals.totalDebit(),
                totals.totalCredit(),
                totals.totalCash(),
                sales.size(),
                cashRegister);

        cashRegister.close();

        CashRegisterClosing savedClosing = cashRegisterClosingRepository.save(cashRegisterClosing);

        return new CashRegisterClosingResponse(savedClosing);
    }

    public Page<CashRegisterClosingResponse> listClosedRegisters(Pageable pageable){
        return cashRegisterClosingRepository.findAll(pageable)
                .map(CashRegisterClosingResponse::new);
    }

    public CashRegisterClosingResponse getClosingByDate(LocalDate date){
        CashRegisterClosing closing = cashRegisterClosingRepository.findByReferenceDate(date)
                .orElseThrow(SalesNotFound::new);

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
