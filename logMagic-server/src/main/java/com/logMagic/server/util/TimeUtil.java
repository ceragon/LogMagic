package com.logMagic.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeUtil {
	private final static Logger log=LogManager.getLogger(TimeUtil.class);
	private static final String TIME_FORMATE_1="yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat format=new SimpleDateFormat(TIME_FORMATE_1);
	public static long getBeginTime(long timeMilli) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(timeMilli);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	public static long getEndTime(long timeMilli) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(timeMilli);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTimeInMillis();
	}
	public static long timeFromStr(String timeStr) {
		long mReturn=-1;
		try {
			Date date=format.parse(timeStr);
			if (date!=null) {
				mReturn=date.getTime();
			}
		} catch (ParseException e) {
			log.error(e,e);
		}
		return mReturn;
	}
	public static String timeToStr(long timeMillins){
		return format.format(new Date(timeMillins));
	}
}
