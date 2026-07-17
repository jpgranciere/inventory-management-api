package com.jpgranciere.inventory.manager.sale.service;

import com.jpgranciere.inventory.manager.exception.InsufficientStockException;
import com.jpgranciere.inventory.manager.exception.ProductNotFoundException;
import com.jpgranciere.inventory.manager.product.entity.Product;
import com.jpgranciere.inventory.manager.product.repository.ProductRepository;
import com.jpgranciere.inventory.manager.sale.dto.*;
import com.jpgranciere.inventory.manager.sale.entity.Sale;
import com.jpgranciere.inventory.manager.sale.entity.SaleItem;
import com.jpgranciere.inventory.manager.sale.repository.SaleRepository;
import com.jpgranciere.inventory.manager.stockmovement.service.StockMovementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final StockMovementService stockMovementService;


    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, StockMovementService stockMovementService) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.stockMovementService = stockMovementService;
    }

    @Transactional
    public SaleResponse registerSale(SaleCreateRequest request){
        Sale sale = new Sale();

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

        saleRepository.save(sale);

        return new SaleResponse(sale);
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
