package com.newbanksystem.spring.services;

import com.newbanksystem.spring.models.Account;
import com.newbanksystem.spring.request.AccountRequest;
import com.newbanksystem.spring.request.TransferRequest;

import java.math.BigDecimal;

public interface BankService { // Package "Service" São as regras de negócio.

    Account createAccount(AccountRequest accountRequest); // Recebe os dados do Front para criar a "Conta"

    void deposit(Integer accountNumber, BigDecimal value);  // Recebe os dados para depósito

    void withdraw(Integer accountNumber, BigDecimal value); // Recebe os dados para saque

    void transfer(TransferRequest transferRequest);
}