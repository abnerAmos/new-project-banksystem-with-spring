package com.newbanksystem.spring.config;

import com.newbanksystem.spring.exceptions.AccountAlreadyExistException;
import com.newbanksystem.spring.exceptions.AddressNotFoundException;
import com.newbanksystem.spring.exceptions.AccountValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerApp {

    // Classe que faz o controle/manipula nossas Exceptions criadas

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<String> addressNotFoundException(AddressNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<String> accountAlreadyExistException(AccountAlreadyExistException e) {
        return ResponseEntity.badRequest().body(e.getMessage());

    }@ExceptionHandler(AccountValidationException.class)
    public ResponseEntity<String> documentInvalidException(AccountValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
