package dev.amerida.qb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableResilientMethods
public class QbApplication {

	static void main(String[] args) {
		SpringApplication.run(QbApplication.class, args);
	}

}
