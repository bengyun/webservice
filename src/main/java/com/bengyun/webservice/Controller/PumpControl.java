package com.bengyun.webservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bengyun.webservice.bean.RequestPumpControlBean;

@RestController
public class PumpControl {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	HttpServletRequest request;

	@Value("${mqtt.broker}")
	private String broker;
	@Value("${mqtt.userName}")
	private String userName;
	@Value("${mqtt.passWord}")
	private String passWord;
	@Value("${mqtt.clientId}")
	private String clientId;

	@RequestMapping(value="/pumpControl/{channelID}", method= RequestMethod.POST)
	public void function1(@RequestBody RequestPumpControlBean ctrlCommand, @PathVariable String channelID) {
		logger.info("Send Pump Command");
		String topic = "channels/" + channelID + "/messages";
        String content = JSON.toJSONString(ctrlCommand);
        try {
        	MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
    		MqttConnectOptions options = new MqttConnectOptions();
    		options.setCleanSession(true);
    		options.setUserName(userName);
    		options.setPassword(passWord.toCharArray());
    		client.connect(options);
    		MqttMessage message = new MqttMessage(content.getBytes());
    		client.publish(topic, message);
    		client.disconnect();
			client.close();
        } catch (MqttException e) {
        	logger.error(e.getMessage());
        } 
	}
}
