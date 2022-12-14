package com.newbanksystem.spring.utils;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import com.newbanksystem.spring.exceptions.AccountValidationException;

import java.util.List;

public class InvalidDocumentUtil {

    public static void checkCpf(String cpf) {

        CPFValidator cpfValidator = new CPFValidator();
        List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
        if (!erros.isEmpty()) {
            throw new AccountValidationException("CPF Invalido");
        }
    }
}