package com.share_thoughts.thoughts_sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class ThoughtsSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThoughtsSyncApplication.class, args);
	}

}
