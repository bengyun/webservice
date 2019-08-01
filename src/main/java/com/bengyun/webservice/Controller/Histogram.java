package com.bengyun.webservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bengyun.webservice.bean.QueryMaxValueModel;
import com.bengyun.webservice.bean.ResponseHistogramBean;
import com.bengyun.webservice.tool.FluxScriptUtils;
import com.bengyun.webservice.tool.InfluxQLUtils;
import com.bengyun.webservice.tool.TimeFormatTool;

@RestController
public class Histogram {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private InfluxQLUtils influxDbUtils;
	@Autowired
	private FluxScriptUtils fluxScriptUtils;
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping(value="/histogram/{publisher}", method= RequestMethod.GET)
	public List<ResponseHistogramBean> getData(
			@PathVariable String publisher
			) {
		logger.info(request.getRequestURI());
		String startTime = TimeFormatTool.GetUTCTimeString(-24 * 60 * 60 * 1000);
		String endTime = TimeFormatTool.GetUTCTimeString();
		float width = (float)10.0;
		List<QueryMaxValueModel> aMaxValueList = influxDbUtils.queryMaxData(
				publisher,
				"water_level",
				startTime,
				endTime);
		if (aMaxValueList.size() > 0) {
			float aMaxValue = Float.valueOf(aMaxValueList.get(0).getMax());
			int levelNumber = (int) ((aMaxValue + width) / width) + 1;
			float[] levels = new float[levelNumber];
			for (int idx = 0; idx < levelNumber; idx ++) {
				levels[idx] = idx * width;
			}
			return fluxScriptUtils.Histogram(
					publisher,
					"water_level",
					startTime,
					endTime,
					levels);
		} else {
			return new ArrayList<>();
		} 
	}
	@RequestMapping(value="/histogram/{publisher}/{starttime}", method= RequestMethod.GET)
	public List<ResponseHistogramBean> getData(
			@PathVariable String publisher,
			@PathVariable String starttime
			) {
		logger.info(request.getRequestURI());
		String startTime = TimeFormatTool.GetUTCTimeString(starttime);
		String endTime = TimeFormatTool.GetUTCTimeString();
		float width = (float)10.0;
		List<QueryMaxValueModel> aMaxValueList = influxDbUtils.queryMaxData(
				publisher,
				"water_level",
				startTime,
				endTime);
		if (aMaxValueList.size() > 0) {
			float aMaxValue = Float.valueOf(aMaxValueList.get(0).getMax());
			int levelNumber = (int) ((aMaxValue + width) / width) + 1;
			float[] levels = new float[levelNumber];
			for (int idx = 0; idx < levelNumber; idx ++) {
				levels[idx] = idx * width;
			}
			return fluxScriptUtils.Histogram(
					publisher,
					"water_level",
					startTime,
					endTime,
					levels);
		} else {
			return new ArrayList<>();
		} 
	}
	@RequestMapping(value="/histogram/{publisher}/{starttime}/{endtime}", method= RequestMethod.GET)
	public List<ResponseHistogramBean> getData(
			@PathVariable String publisher,
			@PathVariable String starttime,
			@PathVariable String endtime
			) {
		logger.info(request.getRequestURI());
		String startTime = TimeFormatTool.GetUTCTimeString(starttime);
		String endTime = TimeFormatTool.GetUTCTimeString(endtime);
		float width = (float)10.0;
		List<QueryMaxValueModel> aMaxValueList = influxDbUtils.queryMaxData(
				publisher,
				"water_level",
				startTime,
				endTime);
		if (aMaxValueList.size() > 0) {
			float aMaxValue = Float.valueOf(aMaxValueList.get(0).getMax());
			int levelNumber = (int) ((aMaxValue + width) / width) + 1;
			float[] levels = new float[levelNumber];
			for (int idx = 0; idx < levelNumber; idx ++) {
				levels[idx] = idx * width;
			}
			return fluxScriptUtils.Histogram(
					publisher,
					"water_level",
					startTime,
					endTime,
					levels);
		} else {
			return new ArrayList<>();
		} 
	}
	@RequestMapping(value="/histogram/{publisher}/{starttime}/{endtime}/{width}", method= RequestMethod.GET)
	public List<ResponseHistogramBean> getData(
			@PathVariable String publisher,
			@PathVariable String starttime,
			@PathVariable String endtime,
			@PathVariable float width
			) {
		logger.info(request.getRequestURI());
		String startTime = TimeFormatTool.GetUTCTimeString(starttime);
		String endTime = TimeFormatTool.GetUTCTimeString(endtime);
		List<QueryMaxValueModel> aMaxValueList = influxDbUtils.queryMaxData(
				publisher,
				"water_level",
				startTime,
				endTime);
		if (aMaxValueList.size() > 0) {
			float aMaxValue = Float.valueOf(aMaxValueList.get(0).getMax());
			int levelNumber = (int) ((aMaxValue + width) / width) + 1;
			float[] levels = new float[levelNumber];
			for (int idx = 0; idx < levelNumber; idx ++) {
				levels[idx] = idx * width;
			}
			return fluxScriptUtils.Histogram(
					publisher,
					"water_level",
					startTime,
					endTime,
					levels);
		} else {
			return new ArrayList<>();
		} 
	}
}
