package com.jpgranciere.inventory.manager.exception;

public class CashRegisterAlreadyOpenException extends RuntimeException {
    public CashRegisterAlreadyOpenException() {

        super("Já existe um caixa aberto");
    }
}
