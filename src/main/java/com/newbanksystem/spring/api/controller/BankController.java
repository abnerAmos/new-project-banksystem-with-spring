package com.newbanksystem.spring.api.controller;

import com.newbanksystem.spring.models.Account;
import com.newbanksystem.spring.request.AccountRequest;
import com.newbanksystem.spring.services.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@RestController
public class BankController {

    /* seria o "main" onde é a porta para cada funcionalidade do projeto;
                                    Aqui é o nosso "End-Point" */

    @Autowired
    private BankService bankService;

    @ResponseStatus(HttpStatus.CREATED)     // Informa qual Status HTTP será informado no Front ou Postman
    @PostMapping("/api/create-account")     // URI de acesso
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {  /* Aonde recebemos os dados Externos,
                                                                            Corpo do request*/

        log.info("BankController.createAccount init"); // "Novo sout", para ver nos logs que a conta foi criada

        Account account = bankService.createAccount(request); // Recebe o Objeto JSON

        log.info("BankController.createAccount end");

        return ResponseEntity.ok(account);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("api/deposit/{accountNumber}")
    public String deposit(@RequestParam("value")BigDecimal value, @PathVariable("accountNumber") Integer number) {

        log.info("BankController.createAccount init");

        bankService.deposit(number, value);

        log.info("BankController.createAccount end");

        return "Deposito efetuado com sucesso";
    }

}
