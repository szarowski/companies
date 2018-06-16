package com.companies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Companies Application class
 */
@SpringBootApplication
public class CompaniesApp extends SpringApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CompaniesApp.class, args);
    }
}