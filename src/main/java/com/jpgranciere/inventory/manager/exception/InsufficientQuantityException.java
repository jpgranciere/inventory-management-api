package com.jpgranciere.inventory.manager.exception;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException() {
        super("A quantidade de entrada/saída deve ser maior que zero");
    }
}
