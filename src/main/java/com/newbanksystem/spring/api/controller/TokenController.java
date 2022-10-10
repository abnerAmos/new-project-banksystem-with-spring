package com.newbanksystem.spring.api.controller;

import com.newbanksystem.spring.cache.model.Token;
import com.newbanksystem.spring.services.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("api/generate-token/{accountNumber}")
    public Token generateToken(@PathVariable Integer accountNumber,
                               @RequestParam String password) {

        log.info("TokenController.generateToken - init - account: {}", accountNumber);

        var token = tokenService.generate(accountNumber, password);

        log.info("TokenController.generateToken - end");

        return token;
    }

}
