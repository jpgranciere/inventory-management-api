package com.jpgranciere.inventory.manager.sale.service;

import com.jpgranciere.inventory.manager.cashier.cashierOpen.entity.CashRegister;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.enums.CashRegisterStatus;
import com.jpgranciere.inventory.manager.cashier.cashierOpen.repository.CashRegisterRepository;
import com.jpgranciere.inventory.manager.exception.CashRegisterNotOpenException;
import com.jpgranciere.inventory.manager.exception.InsufficientStockException;
import com.jpgranciere.inventory.manager.exception.ProductNotFoundException;
import com.jpgranciere.inventory.manager.exception.SalesNotFound;
import com.jpgranciere.inventory.manager.product.entity.Product;
import com.jpgranciere.inventory.manager.product.repository.ProductRepository;
import com.jpgranciere.inventory.manager.sale.dto.*;
import com.jpgranciere.inventory.manager.sale.entity.Sale;
import com.jpgranciere.inventory.manager.sale.entity.SaleItem;
import com.jpgranciere.inventory.manager.sale.repository.SaleRepository;
import com.jpgranciere.inventory.manager.stockmovement.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final StockMovementService stockMovementService;
    private final CashRegisterRepository cashRegisterRepository;

    @Transactional
    public SaleResponse registerSale(SaleCreateRequest request){

        CashRegister cashRegister = cashRegisterRepository.findByCashRegisterStatus(CashRegisterStatus.OPEN)
                .orElseThrow(CashRegisterNotOpenException::new);

        Sale sale = new Sale();
        sale.attachToCashRegister(cashRegister);

        if(request.items() == null || request.items().isEmpty()){
            throw new ProductNotFoundException();
        }

        for (SaleItemRequest item : request.items()){
            Product product = searchProduct(item.productId());
            verifyStock(product, item.quantity());

            var saleItem = buildSaleItem(product, item.quantity());
            sale.addSaleItem(saleItem);
        }
        sale.calculateTotal();
        sale.registerPayment(request);
        sale.verifyMethodPayment();
        sale.validatePayment();

        for (SaleItem item : sale.getSaleItemList()){
            Product product = item.getProduct();
            product.decreaseStock(item.getQuantity());
            stockMovementService.registerExitMovement(product, item.getQuantity());
        }

       Sale savedSale = saleRepository.save(sale);

        return new SaleResponse(savedSale);
    }

    public Page<SaleResponse> getSales(Pageable pageable){
        return saleRepository.findAll(pageable)
                .map(SaleResponse::new);
    }

    public SaleResponse getSaleById(Long id){
        return saleRepository.findById(id)
                .map(SaleResponse::new)
                .orElseThrow(()-> new SalesNotFound());

    }

    //methods
    private Product searchProduct(Long id){
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    private void verifyStock(Product product, int quantity) {
        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException();
        }
    }

    private SaleItem buildSaleItem(Product product, int quantity){
        return new SaleItem(product, quantity);
    }
}
