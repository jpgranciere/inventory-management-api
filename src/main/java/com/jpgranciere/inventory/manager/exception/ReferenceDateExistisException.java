package com.jpgranciere.inventory.manager.exception;

public class ReferenceDateExistisException extends RuntimeException {
    public ReferenceDateExistisException() {
        super("Referencia ja existe");
    }
}
