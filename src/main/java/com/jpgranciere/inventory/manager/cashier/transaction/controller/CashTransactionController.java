package com.jpgranciere.inventory.manager.cashier.transaction.controller;

import com.jpgranciere.inventory.manager.cashier.transaction.dto.CashTransactionCreateRequest;
import com.jpgranciere.inventory.manager.cashier.transaction.dto.CashTransactionResponse;
import com.jpgranciere.inventory.manager.cashier.transaction.service.CashTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cash-registers/current/transactions")
public class CashTransactionController {
    private final CashTransactionService cashTransactionService;

    @PostMapping
    public ResponseEntity<CashTransactionResponse> create(@Valid @RequestBody CashTransactionCreateRequest request, Authentication authentication){
        var response = cashTransactionService.create(request);
        return ResponseEntity.ok(response);
    }
}
