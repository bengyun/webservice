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

import com.bengyun.webservice.tool.MqttGateway;

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
	public void function1(@RequestBody String ctrlCommand, @PathVariable String channelID) {
    logger.info("Send Pump Command");
    logger.info(ctrlCommand);
    String topic = "channels/" + channelID + "/messages";
        String content = ctrlCommand;
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

	/**
	 * 2019_10_14
	 * function_20191014_1()
	 * function_20191014_2()
	 * add new MQTT sender
	 * because origin sender always be disconnected with MQTT broker
	 * */
	@Autowired
    MqttGateway mqttGateway;
	@RequestMapping(value="/pumpControl_20191014/{channelID}", method= RequestMethod.POST)
	public void function_20191014_1(@PathVariable String channelID, @RequestBody String data) {
		logger.info("Send MQTT msg to " + channelID);
        logger.info(data);
        String topic = "channels/" + channelID + "/messages";
        mqttGateway.sendToMqtt(topic, data);
	}
	@RequestMapping(value="/pumpControl_20191014/{channelID}/{data}", method= RequestMethod.GET)
	public void function_20191014_2(@PathVariable String channelID, @PathVariable String data) {
        logger.info("Send MQTT msg to " + channelID);
        logger.info(data);
        String topic = "channels/" + channelID + "/messages";
        mqttGateway.sendToMqtt(topic, data);
	}

	/**
	 * 2019_10_15
	 * function_20191015()
	 * add new MQTT sender for micro service
	 * */
	@RequestMapping(value="/microServiceControl_20191015", method= RequestMethod.POST)
	public void function_20191015_1(@RequestBody String data) {
		logger.info("Send MQTT msg to Micro Service");
        logger.info(data);
        String topic = "channels/7030fe36-c8bb-4adf-8670-cd670acbf321/messages";
        mqttGateway.sendToMqtt(topic, data);
	}
}
