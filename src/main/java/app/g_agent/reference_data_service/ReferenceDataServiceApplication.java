package app.g_agent.reference_data_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class ReferenceDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReferenceDataServiceApplication.class, args);
	}

}
