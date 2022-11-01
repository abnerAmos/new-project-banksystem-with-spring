package com.newbanksystem.spring.services.impl;

import com.newbanksystem.spring.services.BorrowService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Override
    public BigDecimal requestBorrow(Integer accountNumber, String token, BigDecimal amount) {
        return null;
    }
}
