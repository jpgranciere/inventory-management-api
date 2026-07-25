package com.jpgranciere.inventory.manager.exception;

public class SalesNotFoundException extends RuntimeException {
    public SalesNotFoundException(){
        super("Venda não encontrada");
    }
}
