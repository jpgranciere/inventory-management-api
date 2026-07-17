package com.jpgranciere.inventory.manager.exception;

public class DateFutureExeception extends RuntimeException {
    public DateFutureExeception(){
        super("Data não pode ser futura");
    }
}
