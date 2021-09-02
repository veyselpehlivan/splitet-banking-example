package com.splitet.accountexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.splitet.accountexample", "io.splitet"})
public class AccountExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountExampleApplication.class, args);
    }

}
