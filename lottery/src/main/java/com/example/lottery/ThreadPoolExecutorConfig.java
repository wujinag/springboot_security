package com.example.lottery;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@EnableConfigurationProperties(ThreadPoolExecutorProperties.class)
public class ThreadPoolExecutorConfig {

    @Bean(name = "lotteryServiceExecutor")
    public Executor lotteryServiceExecutor(ThreadPoolExecutorProperties poolExecutorProperties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolExecutorProperties.getCorePoolSize());
        executor.setMaxPoolSize(poolExecutorProperties.getMaxPoolSize());
        executor.setQueueCapacity(poolExecutorProperties.getQueueCapacity());
        executor.setThreadNamePrefix(poolExecutorProperties.getNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
