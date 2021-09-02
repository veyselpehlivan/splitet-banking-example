package com.splitet.transactionexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.splitet.accountexample", "io.splitet"})
public class TransactionExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionExampleApplication.class, args);
    }

}
