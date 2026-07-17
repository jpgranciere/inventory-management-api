package com.jpgranciere.inventory.manager.sale.controller;

import com.jpgranciere.inventory.manager.sale.dto.SaleCreateRequest;
import com.jpgranciere.inventory.manager.sale.dto.SaleResponse;
import com.jpgranciere.inventory.manager.sale.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/register-sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<SaleResponse> registerSale(@Valid @RequestBody SaleCreateRequest request, UriComponentsBuilder uriComponentsBuilder){
        var sale = saleService.registerSale(request);

        var uri = uriComponentsBuilder.path("/register-sale/{id}").buildAndExpand(sale.id()).toUri();

        return ResponseEntity.created(uri).body(sale);
    }


}
