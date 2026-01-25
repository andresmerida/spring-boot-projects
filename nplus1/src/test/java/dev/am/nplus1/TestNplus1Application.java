package dev.am.nplus1;

import org.springframework.boot.SpringApplication;

public class TestNplus1Application {

	static void main(String[] args) {
		SpringApplication.from(Nplus1Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
