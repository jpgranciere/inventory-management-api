package com.jpgranciere.inventory.manager.sale.entity;

import com.jpgranciere.inventory.manager.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity(name = "SaleItem")
@Table(name = "sale_item")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;

    public SaleItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        this.subTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
