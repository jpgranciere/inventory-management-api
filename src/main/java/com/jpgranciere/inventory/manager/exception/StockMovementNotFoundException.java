package com.jpgranciere.inventory.manager.exception;

public class StockMovementNotFoundException extends RuntimeException{
    public StockMovementNotFoundException() {
        super("Movimentação não encontrada");
    }
}
