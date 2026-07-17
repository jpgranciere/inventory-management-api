package com.jpgranciere.inventory.manager.exception;

public class SalesNotFoundForReferenceDateException extends RuntimeException {
    public SalesNotFoundForReferenceDateException(){
        super("Venda não encontrada");
    }
}
