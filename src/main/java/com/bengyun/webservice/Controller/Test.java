package com.bengyun.webservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bengyun.webservice.bean.Messages;
import com.bengyun.webservice.tool.InfluxDbUtils;
import com.bengyun.webservice.tool.TimeFormatTool;

@RestController
public class Test {

	@Autowired
	private InfluxDbUtils influxDbUtils;

	@Autowired
	HttpServletRequest request;
	
	/** 之后可能用来获得Authorization的方式
	 * System.out.println(request.getHeader("Authorization"));
	 * System.out.println(request.getRemoteUser());
	 * */

	/**
	 * 返回的时间均为UTC时间
	 * 需要在浏览器转换为当地时间
	 * */
	@RequestMapping(value="/messages/{publisher}", method= RequestMethod.GET)
	public String messages(@PathVariable String publisher) {
		TimeFormatTool utcTimeTool = new TimeFormatTool();
		String startTime = utcTimeTool.GetUTCTimeString(-24 * 60 * 60 * 1000);
		String endTime = utcTimeTool.GetUTCTimeString();
		List<Messages> messagesList = influxDbUtils.query(publisher, startTime, endTime);
		utcTimeTool.ChangeTimeFormat(messagesList);
		return JSON.toJSONString(messagesList);
	}
	@RequestMapping(value="/messages/{publisher}/{starttime}", method= RequestMethod.GET)
	public String messages(@PathVariable String publisher, @PathVariable String starttime) {
		TimeFormatTool utcTimeTool = new TimeFormatTool();
		String startTime = utcTimeTool.GetUTCTimeString(starttime);
		String endTime = utcTimeTool.GetUTCTimeString();
		List<Messages> messagesList = influxDbUtils.query(publisher, startTime, endTime);
		utcTimeTool.ChangeTimeFormat(messagesList);
		return JSON.toJSONString(messagesList);
	}
	@RequestMapping(value="/messages/{publisher}/{starttime}/{endtime}", method= RequestMethod.GET)
	public String messages(@PathVariable String publisher, @PathVariable String starttime, @PathVariable String endtime) {
		TimeFormatTool utcTimeTool = new TimeFormatTool();
		String startTime = utcTimeTool.GetUTCTimeString(starttime);
		String endTime = utcTimeTool.GetUTCTimeString(endtime);
		List<Messages> messagesList = influxDbUtils.query(publisher, startTime, endTime);
		utcTimeTool.ChangeTimeFormat(messagesList);
		return JSON.toJSONString(messagesList);
	}
}
