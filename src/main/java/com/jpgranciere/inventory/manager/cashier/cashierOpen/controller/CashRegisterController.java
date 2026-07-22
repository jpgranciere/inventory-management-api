package com.jpgranciere.inventory.manager.cashier.cashierOpen.controller;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.dto.CashRegisterOpenRequest;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.dto.CashRegisterResponse;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.service.CashRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cash-registers")
@RequiredArgsConstructor
public class CashRegisterController {
    private final CashRegisterService cashRegisterService;

    @PostMapping("/open")
    public ResponseEntity<CashRegisterResponse> open(@Valid @RequestBody CashRegisterOpenRequest request, UriComponentsBuilder uriComponentsBuilder){

        CashRegisterResponse response = cashRegisterService.open(request);
        URI uri = uriComponentsBuilder.path("/cash-registers/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
