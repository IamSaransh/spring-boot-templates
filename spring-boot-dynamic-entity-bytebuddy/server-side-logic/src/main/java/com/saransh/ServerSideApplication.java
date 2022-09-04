package com.saransh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServerSideApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerSideApplication.class, args);
    }
}
