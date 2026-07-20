package com.jpgranciere.inventory.manager.exception;

public class SalesNotFound extends RuntimeException {
    public SalesNotFound(){
        super("Venda não encontrada");
    }
}
