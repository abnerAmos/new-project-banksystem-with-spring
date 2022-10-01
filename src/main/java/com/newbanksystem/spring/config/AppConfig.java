package com.newbanksystem.spring.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching      // Habilita config para utilizar Cache (Redis)
@EnableScheduling   // Cria um agendador
@EnableFeignClients({"com.newbanksystem.spring.client"})    // Faz o consumo da API
public class AppConfig {

    // Faz a integração do projeto com o sistema externo utilizando o padrão REST

}