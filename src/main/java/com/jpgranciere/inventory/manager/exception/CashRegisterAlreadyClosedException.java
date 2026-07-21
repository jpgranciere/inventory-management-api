package com.jpgranciere.inventory.manager.exception;

public class CashRegisterAlreadyClosedException extends RuntimeException {
    public CashRegisterAlreadyClosedException() {
        super("O caixa ja esta fechado");
    }
}
