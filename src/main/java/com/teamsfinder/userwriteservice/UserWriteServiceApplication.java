package com.teamsfinder.userwriteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserWriteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserWriteServiceApplication.class, args);
    }

}
