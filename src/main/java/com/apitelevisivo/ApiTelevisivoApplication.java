package com.apitelevisivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ApiTelevisivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTelevisivoApplication.class, args);
	}
}
