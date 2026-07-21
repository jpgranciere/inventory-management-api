package com.jpgranciere.inventory.manager.exception;

public class CashRegisterNotOpenException extends RuntimeException {
    public CashRegisterNotOpenException() {

        super("O caixa não esta aberto");
    }
}
