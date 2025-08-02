package com.thought_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class ThoughtHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThoughtHubApplication.class, args);
	}

}
