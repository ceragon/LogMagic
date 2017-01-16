package com.logMagic.server.util;

import java.util.Calendar;

public class TimeUtil {
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
}
