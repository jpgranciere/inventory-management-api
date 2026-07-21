package com.jpgranciere.inventory.manager.cashier.cashierClose.controller;

import com.jpgranciere.inventory.manager.cashier.cashierClose.dto.CashRegisterClosingCreateRequest;
import com.jpgranciere.inventory.manager.cashier.cashierClose.dto.CashRegisterClosingResponse;
import com.jpgranciere.inventory.manager.cashier.cashierClose.service.CashRegisterClosingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@RestController
@RequestMapping("/close")
public class CashRegisterClosingController {
    private CashRegisterClosingService cashRegisterClosingService;

    public CashRegisterClosingController(CashRegisterClosingService cashRegisterClosingService) {
        this.cashRegisterClosingService = cashRegisterClosingService;
    }

    @PostMapping
    public ResponseEntity<CashRegisterClosingResponse> closeCashier(@Valid @RequestBody CashRegisterClosingCreateRequest request, UriComponentsBuilder uriBuilder){
        var close = cashRegisterClosingService.registerCashClosing(request);

        var uri = uriBuilder.path("/close/{id}").buildAndExpand(close.id()).toUri();

        return ResponseEntity.created(uri).body(close);
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

















