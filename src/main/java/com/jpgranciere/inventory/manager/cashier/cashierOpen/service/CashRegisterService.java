package com.jpgranciere.inventory.manager.cashier.cashierOpen.service;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.dto.CashRegisterOpenRequest;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.dto.CashRegisterResponse;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.repository.CashRegisterRepository;
import com.jpgranciere.inventory.manager.exception.CashRegisterAlreadyClosedException;
import com.jpgranciere.inventory.manager.exception.CashRegisterAlreadyOpenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashRegisterService {
    private final CashRegisterRepository cashRegisterRepository;

    @Transactional
    public CashRegisterResponse open(CashRegisterOpenRequest cashRegisterOpenRequest){
        verifyRegisterExists();

        CashRegister cashRegister = new CashRegister(cashRegisterOpenRequest.openingBalance());
        CashRegister savedCashRegister = cashRegisterRepository.save(cashRegister);

        return new CashRegisterResponse(savedCashRegister);
    }

    private void verifyRegisterExists(){
        if(cashRegisterRepository.existsByCashRegisterStatus(CashRegisterStatus.OPEN)){
            throw new CashRegisterAlreadyOpenException();
        }
    }
}
