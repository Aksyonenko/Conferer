package com.akqa.kiev.conferer.server;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.Conference;

@Controller
public class ConferenceController {

	private static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");
	
	@Autowired
	private ConferenceDao conferenceDao;
	
	@RequestMapping(value = "/conferences", method = RequestMethod.GET)
	@ResponseBody
	public List<Conference> conferences(@RequestParam(required = false) Long start, @RequestParam(required = false) Long end) {
		Calendar startCalendar, endCalendar;
		Date startDate = (start == null) ? new Date() : new Date(start);
		
		startCalendar = Calendar.getInstance(UTC_TZ);
		startCalendar.setTime(startDate);
		// startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		zeroCalendarTime(startCalendar);
		
		if (end == null) {
			endCalendar = (Calendar) startCalendar.clone();
			endCalendar.add(Calendar.MONTH, 1);
		} else {
			endCalendar = Calendar.getInstance(UTC_TZ);
			endCalendar.setTime(new Date(end));
			zeroCalendarTime(endCalendar);
		}
		
		return conferenceDao.getConferences(startCalendar.getTime(), endCalendar.getTime());
	}
	
	private static void zeroCalendarTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
}
