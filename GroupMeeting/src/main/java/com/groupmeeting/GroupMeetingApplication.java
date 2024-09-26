package com.groupmeeting;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

//@EnableScheduling
@SpringBootApplication
public class GroupMeetingApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupMeetingApplication.class, args);
    }

}
