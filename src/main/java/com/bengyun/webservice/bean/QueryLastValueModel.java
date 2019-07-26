package com.bengyun.webservice.bean;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import com.bengyun.webservice.tool.TimeFormatTool;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Measurement(name = "messages")
public class QueryLastValueModel {
	
	public QueryLastValueModel() {}
	public QueryLastValueModel(String time, String last) {
		this.time = time;
		this.last = last;
	}

	/**
	 *  InfluxDB中时间戳均是以UTC时保存 
	 **/
	/* TimeStamp */
	@Column(name = "time")
	private String time;

	/* Fields */
	@Column(name = "last")
	private String last;
	
	public String getTime() {
		return TimeFormatTool.UTC2Local(time);
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
}
