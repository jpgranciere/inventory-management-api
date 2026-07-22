package com.jpgranciere.inventory.manager.cashier.cashierClose.controller;

import com.jpgranciere.inventory.manager.cashier.cashierClose.dto.CashRegisterClosingResponse;
import com.jpgranciere.inventory.manager.cashier.cashierClose.service.CashRegisterClosingService;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.repository.CashRegisterRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/cash-close")
@RequiredArgsConstructor
public class CashRegisterClosingController {
    private final CashRegisterClosingService cashRegisterClosingService;

    @PostMapping("/{id}/close")
    public ResponseEntity<CashRegisterClosingResponse> close(@PathVariable Long id, UriComponentsBuilder uriBuilder){
        CashRegisterClosingResponse response = cashRegisterClosingService.registerCashClosing(id);

        URI uri = uriBuilder.path("/cash/registers/closings/{closingId}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CashRegisterClosingResponse>> listClosedRegisters(@PageableDefault(size = 10, sort = "id") Pageable pageable){
        return ResponseEntity.ok(cashRegisterClosingService.listClosedRegisters(pageable));
    }

    @GetMapping("/{date}")
    public ResponseEntity<CashRegisterClosingResponse> getClosingByDate (@PathVariable LocalDate date){
        return ResponseEntity.ok(cashRegisterClosingService.getClosingByDate(date));
    }
}

















