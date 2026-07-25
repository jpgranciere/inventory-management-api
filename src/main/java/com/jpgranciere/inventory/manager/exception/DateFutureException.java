package com.jpgranciere.inventory.manager.exception;

public class DateFutureException extends RuntimeException {
    public DateFutureException(){
        super("Data não pode ser futura");
    }
}
