package com.jpgranciere.inventory.manager.exception;

public class UserAlreadyRegistrationException extends RuntimeException {
    public UserAlreadyRegistrationException() {

        super("Usuario ja cadastrado");
    }
}
