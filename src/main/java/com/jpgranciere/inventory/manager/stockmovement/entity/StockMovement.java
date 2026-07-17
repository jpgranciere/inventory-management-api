package com.jpgranciere.inventory.manager.stockmovement.entity;


import com.jpgranciere.inventory.manager.exception.InsufficientPaymentException;
import com.jpgranciere.inventory.manager.exception.InsufficientStockException;
import com.jpgranciere.inventory.manager.product.entity.Product;
import com.jpgranciere.inventory.manager.sale.dto.SaleItemRequest;
import com.jpgranciere.inventory.manager.stockmovement.dto.StockMovementCreateRequest;
import com.jpgranciere.inventory.manager.stockmovement.enums.MovementType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "StockMovement")
@Table(name = "stock_movement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Enumerated(value = EnumType.STRING)
    private MovementType movementType;

    private String observation;
    private LocalDateTime createdAt = LocalDateTime.now();

    public StockMovement(StockMovementCreateRequest stockMovementCreateRequest, Product product) {
        this.product = product;
        this.quantity = stockMovementCreateRequest.quantity();
        this.movementType = stockMovementCreateRequest.movementType();
        this.observation = stockMovementCreateRequest.observation().toUpperCase();
    }

    public StockMovement(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        this.movementType = MovementType.EXIT;
        this.createdAt = LocalDateTime.now();
        this.observation = "sale".toUpperCase();
    }
}
