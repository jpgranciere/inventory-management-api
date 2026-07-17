package com.jpgranciere.inventory.manager.exception;

public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException() {
        super("O valor pago deve ser maior que zero");
    }
}
