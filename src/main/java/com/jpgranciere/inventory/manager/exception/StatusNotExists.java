package com.jpgranciere.inventory.manager.exception;

public class StatusNotExists extends RuntimeException {
    public StatusNotExists() {
        super("Informe um status valido");
    }
}
