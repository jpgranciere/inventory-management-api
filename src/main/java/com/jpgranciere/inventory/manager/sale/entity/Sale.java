package com.jpgranciere.inventory.manager.sale.entity;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.exception.InsufficientPaymentException;
import com.jpgranciere.inventory.manager.exception.InvalidPaymentException;
import com.jpgranciere.inventory.manager.exception.PaymentMethodRequiredException;
import com.jpgranciere.inventory.manager.sale.dto.SaleCreateRequest;
import com.jpgranciere.inventory.manager.sale.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Sale")
@Table(name = "sale")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private BigDecimal total;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sale_id")
    private List<SaleItem> saleItemList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private BigDecimal amountPaid;
    private BigDecimal changeAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_register_id", nullable = false)
    private CashRegister cashRegister;

    public void addSaleItem(SaleItem saleItem) {
        saleItem.setSale(this);
        this.saleItemList.add(saleItem);
    }

    public void registerPayment(SaleCreateRequest request){
        if(request.paymentMethod() == null){
            throw new PaymentMethodRequiredException();
        }
        if(request.amountPaid() == null){
            throw new InsufficientPaymentException();
        }

        setPaymentMethod(request.paymentMethod());
        setAmountPaid(request.amountPaid());
    }

    public void validatePayment(){
        if(this.amountPaid == null || this.amountPaid.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidPaymentException();
        }
        if(this.amountPaid.compareTo(this.total) < 0){
            throw new InsufficientPaymentException();
        }
    }


    public void calculateTotal(){
        BigDecimal totalValue = BigDecimal.ZERO;

        for (SaleItem item : this.getSaleItemList()){
            totalValue = totalValue.add(item.getSubTotal());
        }
        setTotal(totalValue);
    }

    public void verifyMethodPayment() {

        if (this.getPaymentMethod() == null) {
            throw new PaymentMethodRequiredException();
        }

        if (this.getTotal() == null) {
            throw new InsufficientPaymentException();
        }

        switch (this.getPaymentMethod()) {
            case PIX, CREDIT_CARD, DEBIT_CARD -> {
                setAmountPaid(this.getTotal());
                setChangeAmount(BigDecimal.ZERO);
            }
            case CASH -> {
                if (this.getAmountPaid() == null || this.getAmountPaid().compareTo(this.getTotal()) < 0) {
                    throw new InsufficientPaymentException();
                }
                setChangeAmount(this.getAmountPaid().subtract(this.getTotal()));
            }
            default -> throw new PaymentMethodRequiredException();
        }
    }

}
