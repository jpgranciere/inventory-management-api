package com.jpgranciere.inventory.manager.exception;

public class PaymentMethodRequiredException extends RuntimeException {
    public PaymentMethodRequiredException() {
        super("Método de pagamento não encontrado");
    }
}
