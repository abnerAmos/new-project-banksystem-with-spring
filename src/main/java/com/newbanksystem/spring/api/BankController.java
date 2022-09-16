package com.newbanksystem.spring.api;

import com.newbanksystem.spring.models.Account;
import com.newbanksystem.spring.request.AccountRequest;
import com.newbanksystem.spring.services.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BankController {

    /* seria o "main" onde é a porta para cada funcionalidade do projeto;
                                    Aqui é o nosso "End-Point" */

    @Autowired
    private BankService bankService;

    @ResponseStatus(HttpStatus.CREATED)     // Informa qual Status HTTP será informado no Front ou Postman
    @PostMapping("/api/create-account")     // URI de acesso
    public Account createAccount(@RequestBody AccountRequest request) {  // Aonde recebemos os dados Externos

        log.info("BankController.createAccount init"); // "Novo sout", para ver nos log que a conta foi criada

        Account account = bankService.createAccount(request); // Recebe o Objeto JSON

        log.info("BankController.createAccount end");

        return account;
    }

}
