package com.jpgranciere.inventory.manager.product.controller;

import com.jpgranciere.inventory.manager.product.dto.ProductCreateRequest;
import com.jpgranciere.inventory.manager.product.dto.ProductResponse;
import com.jpgranciere.inventory.manager.product.dto.ProductStatusUpdateRequest;
import com.jpgranciere.inventory.manager.product.dto.ProductUpdateRequest;
import com.jpgranciere.inventory.manager.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity createProduct(@Valid @RequestBody ProductCreateRequest request, UriComponentsBuilder uriBuilder){
        var product = productService.createProduct(request);

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.id()).toUri();

        return ResponseEntity.created(uri).body(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> listProducts(@PageableDefault(size = 10, sort= "name") Pageable pageable){
        var page = productService.listProducts(pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        var product = productService.listProductsById(id);

        return ResponseEntity.ok(product);
    }

    @GetMapping("/consult")
    public ResponseEntity<ProductResponse> getProductBySkuOrGtin(@RequestParam(required = false) String sku,@RequestParam(required = false) String gtin){
        var product = productService.getProductBySkuOrGtin(sku, gtin);

        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> patchProductById(@Valid @RequestBody ProductUpdateRequest request, @PathVariable Long id){
        var productUpdate = productService.updateProduct(request, id);

        return ResponseEntity.ok(productUpdate);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductResponse> patchStatusProductById(@Valid @RequestBody ProductStatusUpdateRequest request, @PathVariable Long id){
        return ResponseEntity.ok(productService.updateStatusProduct(request, id));
    }

}
