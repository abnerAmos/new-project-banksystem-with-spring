package com.newbanksystem.spring.exceptions;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException (String message) {
        super(message);
    }
}