package com.danimo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableTransactionManagement
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean("LocationRestApi")
	public RestClient restClient() {
		return RestClient.builder()
				.baseUrl("http://localhost:8000/v1/locations")
				.build();
	}
}
