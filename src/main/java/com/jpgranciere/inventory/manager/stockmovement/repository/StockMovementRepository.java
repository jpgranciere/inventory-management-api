package com.jpgranciere.inventory.manager.stockmovement.repository;

import com.jpgranciere.inventory.manager.product.entity.Product;
import com.jpgranciere.inventory.manager.stockmovement.dto.StockMovementResponse;
import com.jpgranciere.inventory.manager.stockmovement.entity.StockMovement;
import com.jpgranciere.inventory.manager.stockmovement.enums.MovementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    Page<StockMovement> findByProductId(Long id, Pageable pageable);
}
