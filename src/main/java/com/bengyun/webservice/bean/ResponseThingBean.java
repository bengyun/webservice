package com.bengyun.webservice.bean;

public class ResponseThingBean {
	
	public ResponseThingBean() {}
	public ResponseThingBean(
			String thing_id,
			String water_level,
			String water_level_time,
			String battery_voltage,
			String battery_voltage_time) {
		this.thing_id = thing_id;
		this.water_level = water_level;
		this.water_level_time = water_level_time;
		this.battery_voltage = battery_voltage;
		this.battery_voltage_time = battery_voltage_time;
	}
	
	public String thing_id;
	public String water_level;
	public String water_level_time;
	public String battery_voltage;
	public String battery_voltage_time;
}
