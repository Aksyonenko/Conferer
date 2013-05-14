package com.akqa.kiev.conferer.server.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = "application/json")
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

		List<Conference> conferences = conferenceDao.find(startCalendar.getTime(), endCalendar.getTime());
		ObjectWriter writer = objectMapper.writerWithView(Object.class);
		
		try {
			writer.writeValue(response.getOutputStream(), conferences);
		} catch (IOException e) {
			logger.error("Cannot serialize conference list", e);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Conference conference(@PathVariable String id) {
		return conferenceDao.findOne(id);
	}

	private static void zeroCalendarTime(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

}
