package com.newbanksystem.spring.services.impl;

import com.newbanksystem.spring.cache.model.Token;
import com.newbanksystem.spring.cache.repository.TokenRepository;
import com.newbanksystem.spring.exceptions.AccountValidationException;
import com.newbanksystem.spring.models.Account;
import com.newbanksystem.spring.repositories.AccountRepository;
import com.newbanksystem.spring.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${app.token.expiration.minutes}")
    private Integer tokenExpiration;

    private final AccountRepository accountRepository;
    private final TokenRepository tokenRepository;

    @Override
    public Account validateAccount(Integer accountNumber, String password) {

        Account account = accountRepository
                .findFirstByNumber(accountNumber)
                .orElseThrow(() -> {
                    throw new AccountValidationException("conta ou senha inválida");
                });

            if (!account.getPassword().equals(password) || Objects.nonNull(account.getDeactivation()))
                throw new AccountValidationException("conta ou senha inválida");

        return account;
    }

    @Override
    public Token generate(Integer accountNumber, String password) {

        Account account = validateAccount(accountNumber, password);
        var key = UUID.randomUUID().toString();

        var token = Token
                .builder()
                .key(key)
                .accountNumber(accountNumber)
                .generated(LocalDateTime.now())
                .build();

        tokenRepository.save(token);

        return token;
    }

    @Override
    public void validateToken(String token, Integer accountNumber) {

        Token keyValid = tokenRepository.findById(accountNumber)
                .orElseThrow(() -> {throw new AccountValidationException("Token inválido");
            });

        if (!keyValid.getKey().equals(token))
            throw new AccountValidationException("Token inválido");

        if (isTokenExpired(keyValid))
            throw new AccountValidationException("Token expirado");

    }

    private boolean isTokenExpired(Token token) {

        LocalDateTime tokenExpirationTime = token.getGenerated().plusMinutes(tokenExpiration);

        return LocalDateTime.now().isAfter(tokenExpirationTime);
    }
}
