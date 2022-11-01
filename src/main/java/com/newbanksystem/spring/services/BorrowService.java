package com.newbanksystem.spring.services;

import java.math.BigDecimal;

public interface BorrowService {

    BigDecimal requestBorrow(Integer accountNumber, String token, BigDecimal amount);
}
