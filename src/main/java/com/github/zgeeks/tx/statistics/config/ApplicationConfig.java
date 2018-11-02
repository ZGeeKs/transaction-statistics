package com.github.zgeeks.tx.statistics.config;

import com.github.zgeeks.tx.statistics.bucket.BucketService;
import com.github.zgeeks.tx.statistics.bucket.LastMinuteBucketStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ApplicationConfig {

    @Bean
    public BucketService bucketService() {
        return new LastMinuteBucketStorage(Clock.systemUTC());
    }
}
