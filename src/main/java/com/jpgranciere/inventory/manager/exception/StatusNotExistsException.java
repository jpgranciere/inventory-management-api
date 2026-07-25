package com.jpgranciere.inventory.manager.exception;

public class StatusNotExistsException extends RuntimeException {
    public StatusNotExistsException() {
        super("Informe um status valido");
    }
}
