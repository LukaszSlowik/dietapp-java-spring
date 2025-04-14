package org.nutrihealthplan.dietapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DietappApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietappApplication.class, args);
	}

}
