package com.jpgranciere.inventory.manager.product.service;

import com.jpgranciere.inventory.manager.exception.DuplicateSkuException;
import com.jpgranciere.inventory.manager.exception.ProductNotFoundException;
import com.jpgranciere.inventory.manager.product.dto.ProductCreateRequest;
import com.jpgranciere.inventory.manager.product.dto.ProductResponse;
import com.jpgranciere.inventory.manager.product.dto.ProductStatusUpdateRequest;
import com.jpgranciere.inventory.manager.product.dto.ProductUpdateRequest;
import com.jpgranciere.inventory.manager.product.entity.Product;
import com.jpgranciere.inventory.manager.product.enums.ProductStatus;
import com.jpgranciere.inventory.manager.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request){
        validateSku(request.sku());
        Product product = new Product(request);

        return new ProductResponse(repository.save(product));
    }

    public Page<ProductResponse> listProducts(Pageable pageable){
        return repository.findByStatus(ProductStatus.ACTIVE, pageable)
                .map(ProductResponse::new);
    }

    public ProductResponse listProductsById(Long id){
        Product product = searchProduct(id);

        return new ProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest, Long id){
        Product product = searchProduct(id);

        checkSkuDuplication(product, productUpdateRequest.sku(), id);

        product.updateProduct(productUpdateRequest);

        var productSave = repository.save(product);

        return new ProductResponse(productSave);
    }

    @Transactional
    public ProductResponse updateStatusProduct(ProductStatusUpdateRequest productStatusUpdateRequest, Long id){
        Product product = searchProduct(id);

        product.updateStatus(productStatusUpdateRequest);

        return new ProductResponse(repository.save(product));
    }

    public ProductResponse getProductBySkuOrGtin(String sku, String gtin){
        if(sku == null && gtin == null){
            throw new ProductNotFoundException();
        }

        Product product = repository.findBySkuOrGtin(sku, gtin).
                orElseThrow(ProductNotFoundException::new);

        return new ProductResponse(product);
    }
    
    //methods
    private Product searchProduct(Long id){
        return repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    private void validateSku(String sku){
        if(repository.existsBySku(sku)){
            throw new DuplicateSkuException();
        }
    }

    private void checkSkuDuplication(Product product, String sku, Long id){
        if(sku != null){
            if(!sku.equals(product.getSku())){
                if(repository.existsBySkuAndIdNot(sku, id)){
                    throw new DuplicateSkuException();
                }
            }
        }
    }
}
