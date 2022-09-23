package com.newbanksystem.spring.exceptions;

public class AccountAlreadyExistException extends RuntimeException {

    public AccountAlreadyExistException (String message) {
        super(message);
    }
}
