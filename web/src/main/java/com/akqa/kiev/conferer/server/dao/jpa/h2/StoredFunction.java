package com.akqa.kiev.conferer.server.dao.jpa.h2;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class StoredFunction {
	
	public static final String SQL_NAME = "HAPPENS_IN_MONTH_YEAR";
	public static final String JAVA_NAME = "happensInMonthYear";
	
	private static final int[] CALENDAR_TIME_FIELDS = {Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND};
	
	public static Boolean happensInMonthYear(int month, int year, Date eventStart, Date eventEnd, String timezoneId) {
		TimeZone timeZone = TimeZone.getTimeZone(timezoneId);
		
		Calendar point = Calendar.getInstance(timeZone);
		point.set(Calendar.MONTH, month - 1);
		point.set(Calendar.YEAR, year);
		point.set(Calendar.DAY_OF_MONTH, 1);
		
		for (int field : CALENDAR_TIME_FIELDS) {
			point.set(field, 0);
		}
		
		Date from = point.getTime();
		
		point.add(Calendar.MONTH, 1);
		point.add(Calendar.MILLISECOND, -1);

		Date to = point.getTime();
		
		return happensBetween(from, to, eventStart, eventEnd);
	}
	
	public static Boolean happensBetween(Date from, Date to, Date eventStart, Date eventEnd) {
		long p1 = from.getTime();
		long p2 = to.getTime();
		
		long e1 = eventStart.getTime();
		long e2 = eventEnd.getTime();

		boolean result = e1 >= p1 && e2 <= p2; // inner criteria;
		result = result || e1 <= p1 && e2 >= p2; // outer criteria;
		result = result || e1 <= p1 && (e2 >= p1 && e2 <= p2); // starts earlier, finishes inside;
		result = result || (e1 >= p1 && e1 <= p2) && e2 >= p2; // starts inside, finishes later
		
 		return result;
	}
}
