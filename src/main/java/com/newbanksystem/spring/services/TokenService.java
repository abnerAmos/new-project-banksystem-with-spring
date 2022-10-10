package com.newbanksystem.spring.services;

import com.newbanksystem.spring.cache.model.Token;
import com.newbanksystem.spring.models.Account;

public interface TokenService {

    Token generate(Integer accountNumber, String password);

    void validateToken(String token, Integer accountNumber);

    Account validateAccount(Integer accountNumber, String password);
}
