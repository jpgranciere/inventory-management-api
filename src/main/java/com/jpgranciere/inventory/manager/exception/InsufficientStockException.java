package com.jpgranciere.inventory.manager.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("Estoque insuficiente");
    }
}
