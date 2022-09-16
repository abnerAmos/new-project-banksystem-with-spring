package com.newbanksystem.spring.services;

import com.newbanksystem.spring.models.Account;
import com.newbanksystem.spring.request.AccountRequest;
import org.springframework.stereotype.Service;

@Service
public interface BankService { // Package "Service" São as regras de negócio.

    Account createAccount(AccountRequest accountRequest); // Recebe os dados do Front para criar a "Conta"
}
