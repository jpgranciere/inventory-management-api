package com.jpgranciere.inventory.manager.sale.controller;

import com.jpgranciere.inventory.manager.sale.dto.SaleCreateRequest;
import com.jpgranciere.inventory.manager.sale.dto.SaleResponse;
import com.jpgranciere.inventory.manager.sale.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponse> registerSale(@Valid @RequestBody SaleCreateRequest request, UriComponentsBuilder uriComponentsBuilder){
        var sale = saleService.registerSale(request);

        var uri = uriComponentsBuilder.path("/register-sale/{id}").buildAndExpand(sale.id()).toUri();

        return ResponseEntity.created(uri).body(sale);
    }

    @GetMapping
    public ResponseEntity<Page<SaleResponse>> getSale(@PageableDefault(size = 10, sort = "createdAt")Pageable pageable){
        var page = saleService.getSales(pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id){
        var sale = saleService.getSaleById(id);

        return ResponseEntity.ok(sale);
    }


}
