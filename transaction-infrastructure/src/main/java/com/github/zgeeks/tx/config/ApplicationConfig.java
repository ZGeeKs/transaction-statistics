package com.github.zgeeks.tx.config;

import com.github.zgeeks.tx.LastMinuteMonitorStorage;
import com.github.zgeeks.tx.MonitoringTransaction;
import com.github.zgeeks.tx.TransactionService;
import com.github.zgeeks.tx.monitoring.MonitorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ApplicationConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public MonitorService bucketService(Clock clock) {
        return new LastMinuteMonitorStorage(clock);
    }

    @Bean
    public TransactionService transactionService(Clock clock, MonitorService monitorService) {
        return new MonitoringTransaction(clock, monitorService);
    }
}
