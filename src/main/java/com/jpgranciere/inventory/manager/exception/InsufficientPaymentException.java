package com.jpgranciere.inventory.manager.exception;

public class InsufficientPaymentException extends RuntimeException {
    public InsufficientPaymentException(){
        super("Dinheiro Insuficiente");
    }
}
