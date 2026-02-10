package com.companies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsCompaniesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCompaniesApplication.class, args);
	}

}
