package com.newbanksystem.spring.cache.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@RedisHash("generateToken")
public class Token {

    @Id
    private Integer accountNumber;
    private LocalDateTime generated;
    private String key;
}
