package com.bengyun.webservice.bean;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import com.bengyun.webservice.tool.TimeFormatTool;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Measurement(name = "messages")
public class QueryMessageModel {

	public QueryMessageModel() {}
	public QueryMessageModel(
			String time,
			String link,
			String protocol,
			String unit,
			String updateTime,
			Number value,
			String channel,
			String name,
			String publisher) {
		this.time = time;
		this.link = link;
		this.protocol = protocol;
		this.unit = unit;
		this.updateTime = updateTime;
		this.value = value;
		this.channel = channel;
		this.name = name;
		this.publisher = publisher;
	}

	/**
	 *  InfluxDB中时间戳均是以UTC时保存 
	 **/

	/* TimeStamp */
	@Column(name = "time")
	private String time;

	/* Fields */
	@Column(name = "link")
	private String link;
	@Column(name = "protocol")
	private String protocol;
	@Column(name = "unit")
	private String unit;
	@Column(name = "updateTime")
	private String updateTime;
	@Column(name = "value")
	private Number value;

	/* Tags */
	@Column(name = "channel", tag = true)
	private String channel;
	@Column(name = "name", tag = true)
	private String name;
	@Column(name = "publisher", tag = true)
	private String publisher;
	
	/* Getter & Setter */
	public String getTime() {
		return TimeFormatTool.UTC2Local(time);
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Number getValue() {
		return value;
	}
	public void setValue(Number value) {
		this.value = value;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
