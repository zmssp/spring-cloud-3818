package com.sp;

import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class RefreshService {

    private ContextRefresher contextRefresher;

    public RefreshService(ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }

    @Scheduled(fixedDelay = 5000)
    public void refreshContextPeriodically() {
        contextRefresher.refresh();
    }
}