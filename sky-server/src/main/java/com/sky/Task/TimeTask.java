package com.sky.Task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class TimeTask {
    @Scheduled(cron = "0/5 * * * * ?")
    public void timeprint(){
        log.info("每5s定时触发：{}", LocalDateTime.now());
    }
}
