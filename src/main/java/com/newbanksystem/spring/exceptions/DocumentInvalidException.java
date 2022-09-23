package com.newbanksystem.spring.exceptions;

public class DocumentInvalidException extends RuntimeException {

    public DocumentInvalidException (String message) {
        super(message);
    }
}
