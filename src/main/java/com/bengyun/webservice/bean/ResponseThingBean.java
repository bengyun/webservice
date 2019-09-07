package com.bengyun.webservice.bean;

public class ResponseThingBean {
	
	public ResponseThingBean() {}
	public ResponseThingBean(
			String thing_id,
			String water_level,
			String water_level_time,
			String battery_voltage,
			String battery_voltage_time,
			String pump_status,
			String pump_current) {
		this.thing_id = thing_id;
		this.water_level = water_level;
		this.water_level_time = water_level_time;
		this.battery_voltage = battery_voltage;
		this.battery_voltage_time = battery_voltage_time;
		this.pump_status = pump_status;
		this.pump_current = pump_current;
	}
	
	public String thing_id;
	public String water_level;
	public String water_level_time;
	public String battery_voltage;
	public String battery_voltage_time;
	public String pump_status;
	public String pump_current;
}
