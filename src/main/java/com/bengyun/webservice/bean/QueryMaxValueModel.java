package com.bengyun.webservice.bean;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import com.bengyun.webservice.tool.TimeFormatTool;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Measurement(name = "messages")
public class QueryMaxValueModel {
	public QueryMaxValueModel() {}
	public QueryMaxValueModel(String time, String max) {
		this.time = time;
		this.max = max;
	}

	/**
	 *  InfluxDB中时间戳均是以UTC时保存 
	 **/
	/* TimeStamp */
	@Column(name = "time")
	private String time;

	/* Fields */
	@Column(name = "max")
	private String max;
	
	public String getTime() {
		return TimeFormatTool.UTC2Local(time);
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
}
