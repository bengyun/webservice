package com.bengyun.webservice.bean;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Measurement(name = "messages")
public class Messages {

	public Messages() {}
	public Messages(
			String time,
			String link,
			String protocol,
			String unit,
			String updateTime,
			String value,
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
	public String time;

	/* Fields */
	@Column(name = "link")
	public String link;
	@Column(name = "protocol")
	public String protocol;
	@Column(name = "unit")
	public String unit;
	@Column(name = "updateTime")
	public String updateTime;
	@Column(name = "value")
	public String value;

	/* Tags */
	@Column(name = "channel", tag = true)
	public String channel;
	@Column(name = "name", tag = true)
	public String name;
	@Column(name = "publisher", tag = true)
	public String publisher;
}
