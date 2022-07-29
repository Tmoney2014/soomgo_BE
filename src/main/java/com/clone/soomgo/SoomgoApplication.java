package com.clone.soomgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SoomgoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoomgoApplication.class, args);
    }

}
