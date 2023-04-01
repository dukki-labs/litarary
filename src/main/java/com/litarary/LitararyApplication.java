package com.litarary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LitararyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LitararyApplication.class, args);
    }


}
