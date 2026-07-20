package com.jpgranciere.inventory.manager.product.entity;

import com.jpgranciere.inventory.manager.exception.InsufficientQuantityException;
import com.jpgranciere.inventory.manager.exception.InsufficientStockException;
import com.jpgranciere.inventory.manager.exception.StatusNotExists;
import com.jpgranciere.inventory.manager.product.dto.ProductCreateRequest;
import com.jpgranciere.inventory.manager.product.dto.ProductStatusUpdateRequest;
import com.jpgranciere.inventory.manager.product.dto.ProductUpdateRequest;
import com.jpgranciere.inventory.manager.product.enums.ProductCategory;
import com.jpgranciere.inventory.manager.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity(name = "Product")
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String sku;

    @Enumerated(value = EnumType.STRING)
    private ProductCategory category;
    private int stockQuantity;
    private int minStockQuantity;

    @Enumerated(value = EnumType.STRING)
    private ProductStatus status;

    private BigDecimal price;
    private String gtin;

    public Product(ProductCreateRequest request) {
        this.name = request.name();
        this.description = request.description();
        this.sku = request.sku();
        this.category = request.category();
        this.stockQuantity = 0;
        this.minStockQuantity = request.minStockQuantity();
        this.status = ProductStatus.ACTIVE;
        this.price = request.price();
        this.gtin = request.gtin();
    }

    public void updateProduct(ProductUpdateRequest request){
        if(request.name() != null){
            setName(request.name().toUpperCase());
        }
        if(request.description() != null){
            setDescription(request.description().toUpperCase());
        }
        if(request.sku() != null) {
            setSku(request.sku().toUpperCase());
        }
        if(request.category() != null){
            setCategory(request.category());
        }
        if(request.minStockQuantity() != null && request.minStockQuantity() >= 10){
            setMinStockQuantity(request.minStockQuantity());
        }
        if(request.price() != null){
            setPrice(request.price());
        }
        if(request.gtin() != null){
            setGtin(request.gtin());
        }
    }

    public void updateStatus(ProductStatusUpdateRequest request){
        if(request.status() == null){
            throw new StatusNotExists();
        }
        setStatus(request.status());
    }

    public void increaseStock(int quantity){
        if(quantity <= 0){
            throw new InsufficientQuantityException();
        }
        this.stockQuantity += quantity;
    }

    public void decreaseStock(int quantity){
        if(quantity <= 0){
            throw new InsufficientQuantityException();
        }
        if(this.stockQuantity < quantity){
            throw new InsufficientStockException();
        }
        this.stockQuantity -= quantity;

    }
}
