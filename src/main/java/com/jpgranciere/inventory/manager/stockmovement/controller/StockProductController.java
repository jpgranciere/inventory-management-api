package com.jpgranciere.inventory.manager.stockmovement.controller;

import com.jpgranciere.inventory.manager.stockmovement.dto.StockMovementCreateRequest;
import com.jpgranciere.inventory.manager.stockmovement.dto.StockMovementResponse;
import com.jpgranciere.inventory.manager.stockmovement.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/stock-movements")
public class StockProductController {

    private final StockMovementService stockMovementService;

    public StockProductController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    public ResponseEntity<StockMovementResponse> registerMovement(@Valid @RequestBody StockMovementCreateRequest request, UriComponentsBuilder uriBuilder){
        var movement = stockMovementService.registerMovement(request);

        var uri = uriBuilder.path("/stock-movements/{id}").buildAndExpand(movement.id()).toUri();

        return ResponseEntity.created(uri).body(movement);
    }

    @GetMapping
    public ResponseEntity<Page<StockMovementResponse>> listMovement(@PageableDefault(size = 10, sort = "createdAt") Pageable pageable){
        var movement = stockMovementService.listMovement(pageable);

        return ResponseEntity.ok(movement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponse> listMovementById(@PathVariable Long id){
        return ResponseEntity.ok(stockMovementService.listMovementById(id));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Page<StockMovementResponse>> listMovementByProductId(
            @PathVariable Long productId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable){

        return ResponseEntity.ok
                (stockMovementService.listMovementByProductId(productId, pageable));
    }
}
