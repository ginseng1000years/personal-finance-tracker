package com.money.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({"com.money.manager", "com.money.management"})
@EnableMongoRepositories("com.money.management.repository")
public class MoneyManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoneyManagementApplication.class, args);
    }
}