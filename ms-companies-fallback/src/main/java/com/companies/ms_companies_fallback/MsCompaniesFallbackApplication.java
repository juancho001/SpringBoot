package com.companies.ms_companies_fallback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsCompaniesFallbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCompaniesFallbackApplication.class, args);
	}

}
