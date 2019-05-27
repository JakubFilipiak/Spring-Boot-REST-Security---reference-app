package com.jakubfilipiak.restthymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@PropertySource("classpath:config.properties")
@SpringBootApplication
public class RestThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestThymeleafApplication.class, args);
	}

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
