package com.newbanksystem.spring.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class TransferRequest implements Serializable {

    private Integer fromAccount;
    private Integer toAccount;
    private BigDecimal ammount;
}
