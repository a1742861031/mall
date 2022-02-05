package com.athl.gulimall.thridparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GulimallThridPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallThridPartyApplication.class, args);
    }
}
