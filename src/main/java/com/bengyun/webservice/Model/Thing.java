package com.bengyun.webservice.Model;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import lombok.Builder;
import lombok.Data;

/**
+---------------+------+---------+
|         SenML | JSON | Type    |
+---------------+------+---------+
|     Base Name | bn   | String  |
|     Base Time | bt   | Number  |
|     Base Unit | bu   | String  |
|    Base Value | bv   | Number  |
|       Version | bver | Number  |
|          Name | n    | String  |
|          Unit | u    | String  |
|         Value | v    | Number  |
|  String Value | vs   | String  |
| Boolean Value | vb   | Boolean |
|    Data Value | vd   | String  |
|     Value Sum | s    | Number  |
|          Time | t    | Number  |
|   Update Time | ut   | Number  |
+---------------+------+---------+
*/

@Builder
@Data
@Measurement(name = "Thing")
public class Thing {

	/**
	 *  InfluxDB中时间戳均是以UTC时保存 
	 **/
	
	@Column(name = "bn")
	private String bn;
	@Column(name = "bt", tag = true)
	private Number bt;
	@Column(name = "bu", tag = true)
	private String bu;
	@Column(name = "bv", tag = true)
	private Number bv;
	@Column(name = "bver")
	private Number bver;
	@Column(name = "n")
	private String n;
	@Column(name = "u")
	private String u;
	@Column(name = "v")
	private Number v;
	@Column(name = "vs")
	private String vs;
	@Column(name = "vb")
	private Boolean vb;
	@Column(name = "vd")
	private String vd;
	@Column(name = "s")
	private Number s;
	@Column(name = "t")
	private Number t;
	@Column(name = "ut")
	private Number ut;
}
