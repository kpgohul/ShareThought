package com.thought_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")

public class ThoughtStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThoughtStoreApplication.class, args);
	}

}
