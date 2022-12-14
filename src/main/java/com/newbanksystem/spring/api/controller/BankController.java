package com.newbanksystem.spring.api.controller;

import com.newbanksystem.spring.cache.model.WithdrawLimit;
import com.newbanksystem.spring.models.Account;
import com.newbanksystem.spring.request.AccountRequest;
import com.newbanksystem.spring.request.TransferRequest;
import com.newbanksystem.spring.services.BankService;
import com.newbanksystem.spring.services.BorrowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@SuppressWarnings("unused")
@RequiredArgsConstructor    /* Ao utilizar esta Annotations, e adicionarmos "final" nas variáveis de classe
                                podemos substituir a Annotation @Autowired das variáveis */
public class BankController {

    /* seria o "main" onde é a porta para cada funcionalidade do projeto;
                                    Aqui é o nosso "End-Point" */

    private final BankService bankService;
    private final BorrowService borrowService;

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

        log.info("BankController.deposit init");

        bankService.deposit(number, value);

        log.info("BankController.deposit end");

        return "Deposito efetuado com sucesso";
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("api/withdraw/{accountNumber}")
    public String withdraw(@RequestHeader("token") String token,
                           @RequestParam("value")BigDecimal value,
                           @PathVariable("accountNumber") Integer number) {

        log.info("BankController.withdraw init");

        bankService.withdraw(number, value, token);

        var withdraw = new WithdrawLimit().accountNumber(number).addWithdraws(value);

        log.info("BankController.withdraw end");

        return "Saque efetuado com sucesso";
    }

    @PatchMapping("api/transfer")
    public String transfer(@RequestHeader("token") String token,
                           @RequestBody TransferRequest transferRequest) {

        log.info("BankController.transfer - init");

        bankService.transfer(transferRequest, token);

        log.info("BankController.transfer - end");

        return "Transferência realizada com sucesso";
    }
    @PostMapping("api/borrow/{accountNumber}")
    public String borrow(@PathVariable Integer accountNumber,
                         @RequestParam("amount") BigDecimal amount,
                         @RequestHeader("token") String token) {

        log.info("BankController.borrow init");

        BigDecimal value = borrowService.requestBorrow(accountNumber, token, amount);

        log.info("BankController.borrow end");

        return "Empréstimo concedido - Confira sua conta";
    }
}