package com.cleanbox.api;

import java.net.URISyntaxException;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cleanbox.api.business.AguaInfoBean;
import com.cleanbox.api.dao.AguaDAO;
import com.cleanbox.api.integration.Subscriber;

@SpringBootApplication(scanBasePackages = { "com.cleanbox.api" })
public class SpringBootConfiguration {
	
	@Autowired
	Subscriber subs;

	public static void main(String[] args) throws MqttException, URISyntaxException {
		SpringApplication.run(SpringBootConfiguration.class, args);
		
	}
	

}
