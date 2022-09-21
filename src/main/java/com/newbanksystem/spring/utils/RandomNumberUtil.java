package com.newbanksystem.spring.utils;

import com.newbanksystem.spring.repositories.AccountRepository;
import lombok.AllArgsConstructor;

import java.util.Random;

public class RandomNumberUtil {

    public static Integer generateRandomNumber() {

        return new Random().nextInt((1999 - 1001) + 1) + 1001;
    }
}
