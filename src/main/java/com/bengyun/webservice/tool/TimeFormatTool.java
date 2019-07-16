package com.bengyun.webservice.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bengyun.webservice.bean.Messages;

public class TimeFormatTool {

	private SimpleDateFormat sdfLOC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	/**
	 * 获得当前UTC标准时间
	 */
	public String GetUTCTimeString() {
		return GetUTCTimeString(0);
	}
	public String GetUTCTimeString(int offset) {
		Calendar cal = Calendar.getInstance();
		int zone_offset = cal.get(Calendar.ZONE_OFFSET);
		int dst_offset = cal.get(Calendar.DST_OFFSET);
		cal.add(Calendar.MILLISECOND, - (zone_offset + dst_offset) + offset);
		Date timeUTC = cal.getTime();
		return sdfUTC.format(timeUTC);
	}
	public String GetUTCTimeString(String strtime) {
		Date time = null;
		try {
			time = sdfLOC.parse(strtime);
			return sdfUTC.format(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return GetUTCTimeString(0);
		}
	}

	public void ChangeTimeFormat(List<Messages> messagesList){
		Date time = null;
		for(int idx = 0; idx < messagesList.size(); idx++) {
			Messages message = messagesList.get(idx);
			try {
				time = sdfUTC.parse(message.time);
				message.time = sdfLOC.format(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
