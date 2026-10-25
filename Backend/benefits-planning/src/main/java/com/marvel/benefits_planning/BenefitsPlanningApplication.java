package com.marvel.benefits_planning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableCaching
@EnableMethodSecurity
public class BenefitsPlanningApplication {

	public static void main(String[] args) {
		SpringApplication.run(BenefitsPlanningApplication.class, args);
	}

}
