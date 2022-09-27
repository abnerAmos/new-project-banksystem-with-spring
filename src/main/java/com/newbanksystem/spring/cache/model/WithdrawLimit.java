package com.newbanksystem.spring.cache.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RedisHash("withdraws")
public class WithdrawLimit implements Serializable {

    @Id
    private Integer accountNumber;

    private List<BigDecimal> withdraws;

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public WithdrawLimit accountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public List<BigDecimal> getWithdraws() {
        return withdraws;
    }

    public WithdrawLimit addWithdraws(BigDecimal value) {
        if (Objects.isNull(withdraws)) {
            withdraws = new ArrayList<>();
        }

        withdraws.add(value);
        return this;
    }
}