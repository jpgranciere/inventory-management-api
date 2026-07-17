package com.jpgranciere.inventory.manager.sale.repository;

import com.jpgranciere.inventory.manager.product.entity.Product;
import com.jpgranciere.inventory.manager.sale.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    Product findByProductId(Long id);
}
