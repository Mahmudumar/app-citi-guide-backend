package com.tnr.cityguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CityGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(CityGuideApplication.class, args);
    }
}
