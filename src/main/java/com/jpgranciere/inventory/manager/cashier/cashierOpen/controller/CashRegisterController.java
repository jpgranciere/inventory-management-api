package com.jpgranciere.inventory.manager.cashier.cashierOpen.controller;

import com.jpgranciere.inventory.manager.cashier.cashierClose.dto.CashRegisterClosingResponse;
import com.jpgranciere.inventory.manager.cashier.cashierClose.repository.CashRegisterClosingRepository;
import com.jpgranciere.inventory.manager.cashier.cashierClose.service.CashRegisterClosingService;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.dto.CashRegisterOpenRequest;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.dto.CashRegisterResponse;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.service.CashRegisterService;
import com.jpgranciere.inventory.manager.cashier.summary.dto.CashRegisterSummaryResponse;
import com.jpgranciere.inventory.manager.cashier.summary.service.CashRegisterSummaryService;
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
@RequestMapping("/cash-registers")
@RequiredArgsConstructor
public class CashRegisterController {
    private final CashRegisterService cashRegisterService;
    private final CashRegisterClosingService cashRegisterClosingService;
    private final CashRegisterSummaryService cashRegisterSummaryService;

    @PostMapping("/open")
    public ResponseEntity<CashRegisterResponse> open(@Valid @RequestBody CashRegisterOpenRequest request, UriComponentsBuilder uriComponentsBuilder){

        CashRegisterResponse response = cashRegisterService.open(request);
        URI uri = uriComponentsBuilder.path("/cash-registers/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/close")
    public ResponseEntity<CashRegisterClosingResponse> close(UriComponentsBuilder uriBuilder){
        CashRegisterClosingResponse response = cashRegisterClosingService.registerCashClosing();

        URI uri = uriBuilder.path("/cash/registers/closings/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/closings")
    public ResponseEntity<Page<CashRegisterClosingResponse>> listClosedRegisters(@PageableDefault(size = 10, sort = "id") Pageable pageable){
        return ResponseEntity.ok(cashRegisterClosingService.listClosedRegisters(pageable));
    }

    @GetMapping("/closings/{date}")
    public ResponseEntity<CashRegisterClosingResponse> getClosingByDate (@PathVariable LocalDate date){
        return ResponseEntity.ok(cashRegisterClosingService.getClosingByDate(date));
    }

    @GetMapping("/current/summary")
    public ResponseEntity<CashRegisterSummaryResponse> getSummaryResponse(){
        return ResponseEntity.ok(cashRegisterSummaryService.getCurrentSummary());
    }
}
