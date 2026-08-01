package com.jpgranciere.inventory.manager.cashier.cashierOpen.service;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.dto.CashRegisterOpenRequest;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.dto.CashRegisterResponse;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.repository.CashRegisterRepository;
import com.jpgranciere.inventory.manager.exception.CashRegisterAlreadyClosedException;
import com.jpgranciere.inventory.manager.exception.CashRegisterAlreadyOpenException;
import com.jpgranciere.inventory.manager.sale.entity.Sale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashRegisterService {
    private final CashRegisterRepository cashRegisterRepository;

    @Transactional
    public CashRegisterResponse open(CashRegisterOpenRequest cashRegisterOpenRequest){
        verifyRegisterExists();

        CashRegister cashRegister = new CashRegister(cashRegisterOpenRequest.openingBalance());
        CashRegister savedCashRegister = cashRegisterRepository.save(cashRegister);
        log.info("Caixa aberto: id={}, saldoInicial={}", savedCashRegister.getId(), savedCashRegister.getOpeningBalance());

        return new CashRegisterResponse(savedCashRegister);
    }

    private void verifyRegisterExists(){
        if(cashRegisterRepository.existsByCashRegisterStatus(CashRegisterStatus.OPEN)){
            throw new CashRegisterAlreadyOpenException();
        }
    }
}
