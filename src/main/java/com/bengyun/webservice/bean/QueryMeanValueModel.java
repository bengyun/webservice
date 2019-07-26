package com.bengyun.webservice.bean;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import com.bengyun.webservice.tool.TimeFormatTool;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Measurement(name = "messages")
public class QueryMeanValueModel {

	public QueryMeanValueModel() {}
	public QueryMeanValueModel(String time, String mean) {
		this.time = time;
		this.mean = mean;
	}

	/**
	 *  InfluxDB中时间戳均是以UTC时保存 
	 **/
	/* TimeStamp */
	@Column(name = "time")
	private String time;

	/* Fields */
	@Column(name = "mean")
	private String mean;

	public String getTime() {
		return TimeFormatTool.UTC2Local(time);
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
}