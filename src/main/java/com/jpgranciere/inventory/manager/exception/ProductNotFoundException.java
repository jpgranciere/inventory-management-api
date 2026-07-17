package com.jpgranciere.inventory.manager.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(){
        super("Produto não encontrado");
    }
}
