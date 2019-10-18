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

import com.bengyun.webservice.bean.QueryMessageModel;
import com.bengyun.webservice.bean.QueryLastValueModel;
import com.bengyun.webservice.bean.QueryMaxValueModel;
import com.bengyun.webservice.bean.QueryMeanValueModel;

import org.slf4j.Logger;

import lombok.Data;

@Data
public class InfluxQLUtils {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String userName;
	private String password;
	private String url;
	public String database;
	private String retentionPolicy;
	private InfluxDB influxDB;

	public static String policyNamePix = "logRetentionPolicy_";
	public static String RetentionPolicy_Autogen = "autogen";

	public InfluxQLUtils(String userName, String password, String url, String database, String retentionPolicy) {
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

	public List<QueryMessageModel> query(String publisher, String startTime, String endTime){
		String strQuery = "SELECT * FROM \"messages\" ";
		strQuery = strQuery + "WHERE time > \'" + startTime +"\' ";
		strQuery = strQuery + "AND time <= \'" + endTime + "\' ";
		strQuery = strQuery + "AND \"publisher\" = \'" + publisher + "\' ";

		logger.info("Query thing_id: " + publisher + " From " + startTime + " To " + endTime);
		Query query = new Query(strQuery, database);
		QueryResult aQueryResult = influxDB.query(query);

		List<QueryMessageModel> cpuList = new ArrayList<>();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		try {
			cpuList.addAll(resultMapper.toPOJO(aQueryResult, QueryMessageModel.class));
			logger.info("Return " + cpuList.size() + " Of Points");
		}catch(InfluxDBMapperException e) {
			logger.error("mapper queryresult failed, error: {}", e.getMessage());
		}
		return cpuList;
	}
	
	/**
	 * Query As Mean Group By Time
	 * */
	public List<QueryMeanValueModel> queryAsMean(
			String publisher,
			String name,
			String startTime,
			String endTime,
			String groupTime,
			String fill
			) {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT MEAN(\"value\") FROM \"autogen\".\"messages\" ");/* get mean() */
		strQuery.append("WHERE (\"publisher\" = '" + publisher + "' ");/* publisher */
		strQuery.append("AND \"name\" = '" + name + "') ");/* name */
		strQuery.append("AND (time > '" + startTime +"' AND time < '" + endTime + "') ");/* time filter */
		strQuery.append("GROUP BY time(" + groupTime + ") fill(" + fill + ") ");/* group and fill */
		
		logger.info("Query " + name + " of thing:" + publisher + " From " + startTime + " To " + endTime);
		Query query = new Query(strQuery.toString(), database);/* influxDB API */

		List<QueryMeanValueModel> aMeanValueList = new ArrayList<>();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		try {
			QueryResult aQueryResult = influxDB.query(query);/* influxDB API */
			aMeanValueList.addAll(resultMapper.toPOJO(aQueryResult, QueryMeanValueModel.class));/* map result */
			logger.info("Return " + aMeanValueList.size() + " Of Points");
		}catch(InfluxDBMapperException e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}catch(Exception e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}
		return aMeanValueList;
	}
	
	/**
	 * Query As Mean Group By Time
	 * */
	public List<QueryMeanValueModel> queryAsMeanTemp(
			String publisher,
			String name,
			String startTime,
			String endTime,
			String groupTime,
			String fill
			) {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT MEAN(\"value\") FROM \"autogen\".\"messages\" ");/* get mean() */
		strQuery.append("WHERE (\"publisher\" = '" + publisher + "' ");/* publisher */
		strQuery.append("AND (\"name\" = '" + name + "' OR \"name\" = '" + name + "1')) ");/* name */
		strQuery.append("AND (time > '" + startTime +"' AND time < '" + endTime + "') ");/* time filter */
		strQuery.append("GROUP BY time(" + groupTime + ") fill(" + fill + ") ");/* group and fill */
		
		logger.info("Query " + name + " of thing:" + publisher + " From " + startTime + " To " + endTime);
		Query query = new Query(strQuery.toString(), database);/* influxDB API */

		List<QueryMeanValueModel> aMeanValueList = new ArrayList<>();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		try {
			QueryResult aQueryResult = influxDB.query(query);/* influxDB API */
			aMeanValueList.addAll(resultMapper.toPOJO(aQueryResult, QueryMeanValueModel.class));/* map result */
			logger.info("Return " + aMeanValueList.size() + " Of Points");
		}catch(InfluxDBMapperException e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}catch(Exception e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}
		return aMeanValueList;
	}
	
	/**
	 * Query Last Value
	 * */
	public List<QueryLastValueModel> queryLastData(
			String publisher,
			String name
			) {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT LAST(\"value\") FROM \"autogen\".\"messages\" ");/* get last() */
		strQuery.append("WHERE (\"publisher\" = '" + publisher + "' ");/* publisher */
		strQuery.append("AND \"name\" = '" + name + "') ");/* name */

		logger.info("Query last " + name + " of thing_id: " + publisher);
		Query query = new Query(strQuery.toString(), database);/* influxDB API */
		
		List<QueryLastValueModel> aLastValueList = new ArrayList<>();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		try {
			QueryResult aQueryResult = influxDB.query(query);/* influxDB API */
			aLastValueList.addAll(resultMapper.toPOJO(aQueryResult, QueryLastValueModel.class));/* map result */
			logger.info("Return " + aLastValueList.size() + " Of Points");
		}catch(InfluxDBMapperException e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}catch(Exception e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}
		return aLastValueList;
	}
	
	/**
	 * Query Max Value
	 * */
	public List<QueryMaxValueModel> queryMaxData(
			String publisher,
			String name,
			String startTime,
			String endTime
			) {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT MAX(\"value\") FROM \"autogen\".\"messages\" ");/* get last() */
		strQuery.append("WHERE (\"publisher\" = '" + publisher + "' ");/* publisher */
		strQuery.append("AND \"name\" = '" + name + "') ");/* name */
		strQuery.append("AND (time > '" + startTime +"' AND time < '" + endTime + "') ");/* time filter */

		logger.info("Query max " + name + " of thing_id: " + publisher);
		Query query = new Query(strQuery.toString(), database);/* influxDB API */
		
		List<QueryMaxValueModel> aMaxValueList = new ArrayList<>();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		try {
			QueryResult aQueryResult = influxDB.query(query);/* influxDB API */
			aMaxValueList.addAll(resultMapper.toPOJO(aQueryResult, QueryMaxValueModel.class));/* map result */
			logger.info("Return " + aMaxValueList.size() + " Of Points");
		}catch(InfluxDBMapperException e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}catch(Exception e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}
		return aMaxValueList;
	}

	/**
	 * Query Max Value
	 * */
	public List<QueryMaxValueModel> queryMaxDataGroup(
			String publisher,
			String name,
			String startTime,
			String endTime,
			String groupTime,
			String fill
			) {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT MAX(\"value\") FROM \"autogen\".\"messages\" ");/* get last() */
		strQuery.append("WHERE (\"publisher\" = '" + publisher + "' ");/* publisher */
		strQuery.append("AND \"name\" = '" + name + "') ");/* name */
		strQuery.append("AND (time > '" + startTime +"' AND time < '" + endTime + "') ");/* time filter */
		strQuery.append("GROUP BY time(" + groupTime + ") fill(" + fill + ") ");/* group and fill */

		logger.info("Query max " + name + " of thing_id: " + publisher);
		Query query = new Query(strQuery.toString(), database);/* influxDB API */
		
		List<QueryMaxValueModel> aMaxValueList = new ArrayList<>();
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		try {
			QueryResult aQueryResult = influxDB.query(query);/* influxDB API */
			aMaxValueList.addAll(resultMapper.toPOJO(aQueryResult, QueryMaxValueModel.class));/* map result */
			logger.info("Return " + aMaxValueList.size() + " Of Points");
		}catch(InfluxDBMapperException e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}catch(Exception e) {
			logger.error("Query failed, error: {}", e.getMessage());
		}
		return aMaxValueList;
	}
}
