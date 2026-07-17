package com.jpgranciere.inventory.manager.exception;

public class ProductInactiveException extends RuntimeException {
    public ProductInactiveException() {
        super("O produto está inativo");
    }
}
