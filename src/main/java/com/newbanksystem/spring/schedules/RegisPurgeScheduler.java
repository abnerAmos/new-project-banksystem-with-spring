package com.newbanksystem.spring.schedules;

import com.newbanksystem.spring.cache.repository.WithdrawLimitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisPurgeScheduler {

    private final WithdrawLimitRepository withdrawLimitRepository;

    // Todos Scheduler são VOID e Sem Parâmetros

    @Scheduled(cron = "0 0 0 * * ?")    // Reseta todos os dias as 00:00
    public void purgeWithdrawDailyLimit() {

        log.info("RegisPurgeScheduler.purgeWithdrawDailyLimit - Init");

        withdrawLimitRepository.deleteAll();

        log.info("RegisPurgeScheduler.purgeWithdrawDailyLimit - End");
    }
}
