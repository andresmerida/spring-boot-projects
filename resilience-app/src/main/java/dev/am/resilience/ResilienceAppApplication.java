package dev.am.resilience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableResilientMethods
public class ResilienceAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResilienceAppApplication.class, args);
	}

}
