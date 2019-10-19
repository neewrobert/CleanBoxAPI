package com.cleanbox.api.integration;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cleanbox.api.business.AguaInfoBean;

@Component
public class Subscriber implements MqttCallback {

	private String topic = "dados";
	private MqttClient client;
	private AguaInfoBean bean; 
	
	@Autowired
	public Subscriber(AguaInfoBean bean) throws MqttException, URISyntaxException {
		this(new URI("tcp://sctjsukp:3uZyReh212Pd@soldier.cloudmqtt.com:11425"));
		this.bean = bean;
				
	}
	
	public Subscriber() {
		
	}

	public Subscriber(URI uri) throws MqttException {
		String host = String.format("tcp://%s:%d", uri.getHost(), uri.getPort());
		String[] auth = this.getAuth(uri);
		String username = auth[0];
		String password = auth[1];
		String clientId = "MQTT-Java-CleanBox";
		if (!uri.getPath().isEmpty()) {
			this.topic = uri.getPath().substring(1);
		}

		MqttConnectOptions conOpt = new MqttConnectOptions();
		conOpt.setCleanSession(true);
		conOpt.setUserName(username);
		conOpt.setPassword(password.toCharArray());

		this.client = new MqttClient(host, clientId, new MemoryPersistence());
		this.client.setCallback(this);
		this.client.connect(conOpt);

		this.client.subscribe(this.topic);
	}

	private String[] getAuth(URI uri) {
		String a = uri.getAuthority();
		String[] first = a.split("@");
		return first[0].split(":");
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {

		System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
		
		
		bean.gravaMsgMQTT(new String(message.getPayload()));
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}

}
