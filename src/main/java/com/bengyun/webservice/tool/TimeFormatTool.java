package com.bengyun.webservice.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormatTool {

	private static SimpleDateFormat sdfSIM = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	/**
	 * 获得当前UTC标准时间
	 */
	public static String GetUTCTimeString() {
		return GetUTCTimeString(0);
	}
	public static String GetUTCTimeString(int offset) {
		Calendar cal = Calendar.getInstance();
		int zone_offset = cal.get(Calendar.ZONE_OFFSET);
		int dst_offset = cal.get(Calendar.DST_OFFSET);
		cal.add(Calendar.MILLISECOND, - (zone_offset + dst_offset) + offset);
		Date timeUTC = cal.getTime();
		return sdfUTC.format(timeUTC);
	}
	public static String GetUTCTimeString(String strtime) {
		Date time = null;
		try {
			time = sdfSIM.parse(strtime);
			return sdfUTC.format(time);
		} catch (ParseException e) {
			e.printStackTrace();
			return GetUTCTimeString(0);
		}
	}
	
	public static String UTC2Local(String UTCTime) {
		try {
			return sdfSIM.format(sdfUTC.parse(UTCTime));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
