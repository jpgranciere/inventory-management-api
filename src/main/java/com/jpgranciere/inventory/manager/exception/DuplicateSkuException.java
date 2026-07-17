package com.jpgranciere.inventory.manager.exception;

public class DuplicateSkuException extends RuntimeException{
    public DuplicateSkuException() {
        super("SKU duplicado");
    }
}
