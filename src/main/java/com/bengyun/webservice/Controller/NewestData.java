package com.bengyun.webservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bengyun.webservice.bean.QueryLastValueModel;
import com.bengyun.webservice.bean.RequestThingListBean;
import com.bengyun.webservice.bean.ResponseThingBean;
import com.bengyun.webservice.tool.InfluxDbUtils;

@RestController
public class NewestData {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private InfluxDbUtils influxDbUtils;
	
	@Autowired
	HttpServletRequest request;

	/**
	 * UTC time in response
	 * */
	@RequestMapping(value="/newestData", method= RequestMethod.POST)
	public List<ResponseThingBean> getData(@RequestBody RequestThingListBean thingList) {
		logger.info(request.getRequestURI());
		List<String> things = thingList.getThings();
		List<ResponseThingBean> aResponseThingList = new ArrayList<>();
		for(int idx = 0; idx < things.size(); idx++) {
			String thing_id = things.get(idx);
			/* query water_level & battery_voltage by thing_id */
			List<QueryLastValueModel> aLastWaterLevel = influxDbUtils.queryLastData(thing_id, "water_level");
			List<QueryLastValueModel> aLastBatteryVoltage = influxDbUtils.queryLastData(thing_id, "battery_voltage");
			/* return water_level & water_level_time & battery_voltage & battery_voltage_time */
			ResponseThingBean aResponseThingBean = new ResponseThingBean();
			aResponseThingBean.thing_id = thing_id;
			aResponseThingBean.water_level = aLastWaterLevel.size() == 0 ? null : aLastWaterLevel.get(0).getLast();
			aResponseThingBean.water_level_time = aLastWaterLevel.size() == 0 ? null : aLastWaterLevel.get(0).getTime();
			aResponseThingBean.battery_voltage = aLastBatteryVoltage.size() == 0 ? null : aLastBatteryVoltage.get(0).getLast();
			aResponseThingBean.battery_voltage_time = aLastBatteryVoltage.size() == 0 ? null : aLastBatteryVoltage.get(0).getTime();
			aResponseThingList.add(aResponseThingBean);
		}
		return aResponseThingList;
	}
}
