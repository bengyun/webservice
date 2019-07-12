package com.bengyun.webservice.Model;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import lombok.Data;

@Data
public class InfluxDbUtils {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String userName;
	private String password;
	private String url;
	public String database;
	private String retentionPolicy;
	private InfluxDB influxDB;

	// 数据保存策略
	public static String policyNamePix = "logRetentionPolicy_";
	public static String RetentionPolicy_Autogen = "autogen";

	public InfluxDbUtils(String userName, String password, String url, String database, String retentionPolicy) {
		this.userName = userName;
		this.password = password;
		this.url = url;
		this.database = database;
		this.retentionPolicy = retentionPolicy == null || "".equals(retentionPolicy) ? RetentionPolicy_Autogen : retentionPolicy;
		this.influxDB = influxDbBuild();
	}

	/**
	 * 连接数据库 ，若不存在则创建
	 *
	 * @return influxDb实例
	 **/
	private InfluxDB influxDbBuild() {
		if (influxDB == null) {
			influxDB = InfluxDBFactory.connect(url, userName, password);
		}
		try {
			influxDB.setDatabase(database);
		} catch (Exception e) {
			logger.error("connect influx db failed, error: {}", e.getMessage());
		} finally {
			influxDB.setRetentionPolicy(retentionPolicy);
		}
		influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
		return influxDB;
	}
}
