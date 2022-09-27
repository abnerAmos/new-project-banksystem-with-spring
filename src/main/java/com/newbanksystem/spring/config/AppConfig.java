package com.newbanksystem.spring.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@EnableFeignClients({"com.newbanksystem.spring.client"})
public class AppConfig {

    // Faz a integração do projeto com o sistema externo utilizando o padrão REST

}