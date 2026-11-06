package com.appcitiguide.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point.
 * Keep this class in the root package so Spring discovers components
 * in the controller, service, repository, entity, and config packages.
 */
@SpringBootApplication
public class AppCitiGuideBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppCitiGuideBackendApplication.class, args);
    }
}
