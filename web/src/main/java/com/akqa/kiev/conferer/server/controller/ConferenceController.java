package com.akqa.kiev.conferer.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.model.Conference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Controller
@RequestMapping("/conferences")
public class ConferenceController {

	private static Logger logger = LoggerFactory.getLogger(ConferenceController.class);
	
	private static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");

	@Autowired
	private ConferenceDao conferenceDao;
	
	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public void conferences(
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			HttpServletResponse response) {

		Calendar startCalendar = Calendar.getInstance(UTC_TZ);
		zeroCalendarTime(startCalendar);

		if (year != null && month != null) {
			startCalendar.set(Calendar.YEAR, year);
			startCalendar.set(Calendar.MONTH, month - 1);
		}

		Calendar endCalendar = (Calendar) startCalendar.clone();
		endCalendar.add(Calendar.MONTH, 1);
		endCalendar.add(Calendar.DAY_OF_YEAR, -1);

		List<Conference> conferences = conferenceDao.find(startCalendar.getTime(), endCalendar.getTime());
		ObjectWriter writer = objectMapper.writerWithView(Object.class);
		
		try {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");
			
			writer.writeValue(response.getOutputStream(), conferences);
		} catch (IOException e) {
			logger.error("Cannot serialize conference list", e);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Conference conference(@PathVariable String id) {
		try {
			Conference conference = conferenceDao.findOne(id);
			if (conference == null) throw new IncorrectResultSizeDataAccessException(1, 0);
			
			return conference;
			
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new ResourceNotFoundException("Conference " + id);
		}
	}
	
	@RequestMapping("/months")
	@ResponseBody
	public List<Long> months() {
		List<Conference> conferences = conferenceDao.findAll();
		Set<Long> dates = new HashSet<>();
		
		for (Conference conference : conferences) {
			/*for (Date date : new Date[] {conference.getStartDate(), conference.getEndDate()}) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				
				for (int field : new int[] {Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}) {
					calendar.set(field, 0);
				}
				
				dates.add(calendar.getTimeInMillis());
			}*/
			
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(conference.getStartDate());
			
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(conference.getEndDate());
			
			for (int field : new int[] {Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}) {
				startCalendar.set(field, 0);
			}
			
			do {
				dates.add(startCalendar.getTimeInMillis());
				startCalendar.add(Calendar.MONTH, 1);
			} while (startCalendar.before(endCalendar));
		}
		
		List<Long> sortedDates = new ArrayList<>(dates);
		Collections.sort(sortedDates);
		
		return sortedDates;
	}

	private static void zeroCalendarTime(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

}
