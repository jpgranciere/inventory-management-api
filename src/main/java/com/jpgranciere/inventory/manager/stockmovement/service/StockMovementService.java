package com.jpgranciere.inventory.manager.stockmovement.service;

import com.jpgranciere.inventory.manager.exception.ProductInactiveException;
import com.jpgranciere.inventory.manager.exception.ProductNotFoundException;
import com.jpgranciere.inventory.manager.exception.StockMovementNotFoundException;
import com.jpgranciere.inventory.manager.product.entity.Product;
import com.jpgranciere.inventory.manager.product.enums.ProductStatus;
import com.jpgranciere.inventory.manager.product.repository.ProductRepository;
import com.jpgranciere.inventory.manager.stockmovement.dto.StockMovementCreateRequest;
import com.jpgranciere.inventory.manager.stockmovement.dto.StockMovementResponse;
import com.jpgranciere.inventory.manager.stockmovement.entity.StockMovement;
import com.jpgranciere.inventory.manager.stockmovement.enums.MovementType;
import com.jpgranciere.inventory.manager.stockmovement.repository.StockMovementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StockMovementService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public StockMovementService(ProductRepository productRepository, StockMovementRepository stockMovementRepository){
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Transactional
    public StockMovementResponse registerMovement(StockMovementCreateRequest stockMovementCreateRequest){
        Product product = searchProduct(stockMovementCreateRequest.productId());

        verifyStatusProduct(product);

        applyMovementToProduct(product, stockMovementCreateRequest);

        productRepository.save(product);

        var stockMovement = createStockMovement(stockMovementCreateRequest, product);
        stockMovementRepository.save(stockMovement);

        return new StockMovementResponse(stockMovement);
    }

    public Page<StockMovementResponse> listMovement(Pageable pageable){
        return stockMovementRepository.findAll(pageable)
                .map(StockMovementResponse::new);
    }

    public StockMovementResponse listMovementById(Long id){
        StockMovement stockMovement = searchMovement(id);
        return new StockMovementResponse(stockMovement);
    }

    public Page<StockMovementResponse> listMovementByProductId(Long id, Pageable pageable){

        return stockMovementRepository.findByProductId(id, pageable)
                .map(StockMovementResponse::new);
    }


    //methods
    public StockMovement registerExitMovement(Product product, int quantity){
        StockMovement stockMovement = new StockMovement(product, quantity);
        return stockMovementRepository.save(stockMovement);
    }

    private Product searchProduct(Long id){
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    private void applyMovementToProduct(Product product, StockMovementCreateRequest request){
        if(request.movementType().equals(MovementType.ENTRY)){
            product.increaseStock(request.quantity());
        }
        if(request.movementType().equals(MovementType.EXIT)){
            product.decreaseStock(request.quantity());
        }
    }

    private StockMovement createStockMovement(StockMovementCreateRequest stockMovementCreateRequest, Product product){
        return new StockMovement(stockMovementCreateRequest, product);
    }

    private StockMovement searchMovement(Long id){
        return stockMovementRepository.findById(id)
                .orElseThrow(StockMovementNotFoundException::new);
    }

    private void verifyStatusProduct(Product product){
        if(product.getStatus().equals(ProductStatus.INACTIVE)){
            throw new ProductInactiveException();
        }
    }
}
