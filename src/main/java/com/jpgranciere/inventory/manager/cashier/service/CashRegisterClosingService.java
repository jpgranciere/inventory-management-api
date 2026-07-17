package com.jpgranciere.inventory.manager.cashier.service;

import com.jpgranciere.inventory.manager.cashier.dto.CashRegisterClosingCreateRequest;
import com.jpgranciere.inventory.manager.cashier.dto.CashRegisterClosingResponse;
import com.jpgranciere.inventory.manager.cashier.dto.TotalSummary;
import com.jpgranciere.inventory.manager.cashier.entity.CashRegisterClosing;
import com.jpgranciere.inventory.manager.cashier.enums.ClosingStatus;
import com.jpgranciere.inventory.manager.cashier.repository.CashRegisterClosingRepository;
import com.jpgranciere.inventory.manager.exception.DateFutureExeception;
import com.jpgranciere.inventory.manager.exception.InsufficientStockException;
import com.jpgranciere.inventory.manager.exception.ReferenceDateExistisException;
import com.jpgranciere.inventory.manager.exception.SalesNotFoundForReferenceDateException;
import com.jpgranciere.inventory.manager.sale.entity.Sale;
import com.jpgranciere.inventory.manager.sale.repository.SaleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class CashRegisterClosingService {
    private final CashRegisterClosingRepository cashRegisterClosingRepository;
    private final SaleRepository saleRepository;

    public CashRegisterClosingService(CashRegisterClosingRepository cashRegisterClosingRepository, SaleRepository saleRepository) {
        this.cashRegisterClosingRepository = cashRegisterClosingRepository;
        this.saleRepository = saleRepository;
    }

    @Transactional
    public CashRegisterClosingResponse registerCashClosing(CashRegisterClosingCreateRequest request){
        verifyFutureDate(request.referenceDate());

        existsByDateAndStatus(request.referenceDate());

        LocalDateTime start = request.referenceDate().atStartOfDay();
        LocalDateTime end =  request.referenceDate().plusDays(1).atStartOfDay();

        List<Sale> sales = saleRepository.findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(start,end);

        if(sales.isEmpty()){
            throw new SalesNotFoundForReferenceDateException();
        }

        TotalSummary totals = calculateClosingResponse(sales);

        CashRegisterClosing cashRegisterClosing = new CashRegisterClosing(
                request.referenceDate(),
                totals.totalSales(),
                totals.totalPix(),
                totals.totalDebit(),
                totals.totalCredit(),
                totals.totalCash(),
                sales.size());

        return new CashRegisterClosingResponse(cashRegisterClosingRepository.save(cashRegisterClosing));
    }

    public Page<CashRegisterClosingResponse> listClosedRegisters(Pageable pageable){
        return cashRegisterClosingRepository.findAll(pageable)
                .map(CashRegisterClosingResponse::new);
    }

    public CashRegisterClosingResponse getClosingByDate(LocalDate date){
        CashRegisterClosing closing = cashRegisterClosingRepository.findByReferenceDate(date)
                .orElseThrow(SalesNotFoundForReferenceDateException::new);

        return new CashRegisterClosingResponse(closing);
    }

    //methods
    private void verifyFutureDate(LocalDate date){
        LocalDate today = LocalDate.now();

        if(date.isAfter(today)){
            throw new DateFutureExeception();
        }
    }

    private void existsByDateAndStatus(LocalDate date){
        if(cashRegisterClosingRepository.existsByReferenceDateAndClosingStatus(date, ClosingStatus.CLOSED)){
            throw new ReferenceDateExistisException();
        }
    }

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

        return new TotalSummary(totalSales, totalPix, totalDebit, totalCredit, totalCredit);
    }


}
