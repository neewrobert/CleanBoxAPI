package com.cleanbox.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cleanbox.api.integration.Subscriber;

@SpringBootApplication(scanBasePackages = { "com.cleanbox.api" })
public class SpringBootConfiguration {
	
	@Autowired
	Subscriber subs;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootConfiguration.class, args);
		
	}
	

}
