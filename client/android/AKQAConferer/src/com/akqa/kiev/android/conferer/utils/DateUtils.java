package com.akqa.kiev.android.conferer.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.akqa.kiev.android.conferer.model.SessionData;

public final class DateUtils {

	private DateUtils() {
	}

	public static int getNearestToCurrentDateIndex(List<Long> datesInMillisList) {
		if (datesInMillisList != null && !datesInMillisList.isEmpty()) {
			Collections.sort(datesInMillisList);
			Date now = Calendar.getInstance().getTime();
			int i = 0;
			for (i = 0; i < datesInMillisList.size(); i++) {
				Date date = new Date(datesInMillisList.get(i));
				if (date.after(now)) {
					return i;
				}
			}
			return i - 1;
		}
		return -1;
	}

	public static Map<Date, List<SessionData>> sortSessionsByDays(
			List<SessionData> sessionDatas) {
		Map<Date, List<SessionData>> result = new TreeMap<Date, List<SessionData>>();
		if (sessionDatas != null && !sessionDatas.isEmpty()) {
			Collections.sort(sessionDatas);
			Date date = truncateToDays(sessionDatas.get(0).getStartTime());
			List<SessionData> list = new ArrayList<SessionData>();
			for (SessionData sessionData : sessionDatas) {
				Date sessionStartTime = sessionData.getStartTime();
				if (isSameDay(date, sessionStartTime)) {
					list.add(sessionData);
				} else {
					result.put(date, list);
					list = new ArrayList<SessionData>();
					date = truncateToDays(sessionStartTime);
					list.add(sessionData);
				}
			}
			result.put(date, list);
		}
		return result;
	}

	private static boolean isSameDay(Date date, Date anotherDate) {
		if (date == null || anotherDate == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar anotherCal = Calendar.getInstance();
		anotherCal.setTime(anotherDate);
		return isSameDay(cal, anotherCal);
	}

	private static boolean isSameDay(Calendar cal, Calendar anotherCal) {
		if (cal == null || anotherCal == null) {
			return false;
		}
		return (cal.get(Calendar.ERA) == anotherCal.get(Calendar.ERA)
				&& cal.get(Calendar.YEAR) == anotherCal.get(Calendar.YEAR) && cal
					.get(Calendar.DAY_OF_YEAR) == anotherCal
				.get(Calendar.DAY_OF_YEAR));
	}

	private static Date truncateToDays(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

}
