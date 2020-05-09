package com.example.EurekaDiscoveryApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaDiscoveryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscoveryAppApplication.class, args);
	}

}
