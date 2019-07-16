package com.bengyun.webservice.tool;

import java.util.ArrayList;
import java.util.List;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDBMapperException;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.slf4j.LoggerFactory;

import com.bengyun.webservice.bean.Messages;

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

	public List<Messages> query(String publisher, String startTime, String endTime){
		String strQuery = "SELECT * FROM \"messages\" ";
		strQuery = strQuery + "WHERE time > \'" + startTime +"\' ";
		strQuery = strQuery + "AND time <= \'" + endTime + "\' ";
		strQuery = strQuery + "AND \"publisher\" = \'" + publisher + "\' ";

		logger.info("Query thing_id: " + publisher + " From " + startTime + " To " + endTime);
		Query query = new Query(strQuery, database);
		QueryResult aQueryResult = influxDB.query(query);

		List<Messages> cpuList = new ArrayList<>();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		try {
			cpuList.addAll(resultMapper.toPOJO(aQueryResult, Messages.class));
			logger.info("Return " + cpuList.size() + " Of Points");
		}catch(InfluxDBMapperException e) {
			logger.error("mapper queryresult failed, error: {}", e.getMessage());
		}
		return cpuList;
	}
}
