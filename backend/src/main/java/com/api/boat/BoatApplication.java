package com.api.boat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
public class BoatApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoatApplication.class, args);
    }
}
