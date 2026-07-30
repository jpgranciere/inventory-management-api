package com.jpgranciere.inventory.manager.exception;

public class InsufficientCashBalanceException extends RuntimeException {
    public InsufficientCashBalanceException() {
        super("Saldo insuficiente");
    }
}
