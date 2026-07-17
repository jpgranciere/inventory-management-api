package com.jpgranciere.inventory.manager.product.repository;

import com.jpgranciere.inventory.manager.product.entity.Product;
import com.jpgranciere.inventory.manager.product.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);

    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    boolean existsBySkuAndIdNot(String sku, Long id);
}
