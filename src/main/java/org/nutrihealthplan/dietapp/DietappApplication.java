package org.nutrihealthplan.dietapp;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableCaching
public class DietappApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietappApplication.class, args);
	}


}
