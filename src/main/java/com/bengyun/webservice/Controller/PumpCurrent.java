package com.bengyun.webservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bengyun.webservice.bean.QueryMeanValueModel;
import com.bengyun.webservice.tool.InfluxQLUtils;
import com.bengyun.webservice.tool.TimeFormatTool;

@RestController
public class PumpCurrent {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private InfluxQLUtils influxDbUtils;
	@Autowired
	HttpServletRequest request;
	
	/**
	 * UTC time in request and response
	 * */
	@RequestMapping(value="/pumpCurrent/{publisher}", method= RequestMethod.GET)
	public List<QueryMeanValueModel> getData(
			@PathVariable String publisher
			) {
		logger.info("Override 1: " + request.getRequestURI());
		String startTime = TimeFormatTool.GetUTCTimeString(-24 * 60 * 60 * 1000);/* now - 24h */
		String endTime = TimeFormatTool.GetUTCTimeString();/* now */
		String groupTime = "10s";/* group 1h */
		String fill = "null";/* fill null in missing data */
		List<QueryMeanValueModel> aWaterLevelList = influxDbUtils.queryAsMean(
				publisher,
				"pump_current",
				startTime,
				endTime,
				groupTime,
				fill);
		return aWaterLevelList;
	}
	@RequestMapping(value="/pumpCurrent/{publisher}/{starttime}", method= RequestMethod.GET)
	public List<QueryMeanValueModel> getData(
			@PathVariable String publisher,
			@PathVariable String starttime
			) {
		logger.info("Override 2: " + request.getRequestURI());
		String startTime = TimeFormatTool.GetUTCTimeString(starttime);
		String endTime = TimeFormatTool.GetUTCTimeString();
		String groupTime = "1h";
		String fill = "null";
		List<QueryMeanValueModel> aWaterLevelList = influxDbUtils.queryAsMean(
				publisher,
				"pump_current",
				startTime,
				endTime,
				groupTime,
				fill);
		return aWaterLevelList;
	}
	@RequestMapping(value="/pumpCurrent/{publisher}/{starttime}/{endtime}", method= RequestMethod.GET)
	public List<QueryMeanValueModel> getData(
			@PathVariable String publisher,
			@PathVariable String starttime,
			@PathVariable String endtime
			) {
		logger.info("Override 3: " + request.getRequestURI());
		String startTime = TimeFormatTool.GetUTCTimeString(starttime);
		String endTime = TimeFormatTool.GetUTCTimeString(endtime);
		String groupTime = "1h";
		String fill = "null";
		List<QueryMeanValueModel> aWaterLevelList = influxDbUtils.queryAsMean(
				publisher,
				publisher + "pump_current",
				startTime,
				endTime,
				groupTime,
				fill);
		return aWaterLevelList;
	}
	@RequestMapping(value="/pumpCurrent/{publisher}/{starttime}/{endtime}/{grouptime}/{fill}", method= RequestMethod.GET)
	public List<QueryMeanValueModel> getData(
			@PathVariable String publisher,
			@PathVariable String starttime,
			@PathVariable String endtime,
			@PathVariable String grouptime,
			@PathVariable String fill
			) {
		logger.info("Override 4: " + request.getRequestURI());
		String startTime = TimeFormatTool.GetUTCTimeString(starttime);
		String endTime = TimeFormatTool.GetUTCTimeString(endtime);
		String groupTime = grouptime;
		String afill = fill;
		List<QueryMeanValueModel> aWaterLevelList = influxDbUtils.queryAsMean(
				publisher,
				"pump_current",
				startTime,
				endTime,
				groupTime,
				afill);
		return aWaterLevelList;
	}
}
