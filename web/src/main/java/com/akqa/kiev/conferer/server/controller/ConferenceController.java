package com.akqa.kiev.conferer.server.controller;

import java.io.IOException;
import java.math.BigInteger;
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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.model.Conference;
import com.akqa.kiev.conferer.server.model.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Controller
@RequestMapping("/conferences")
public class ConferenceController extends AbstractConfererController<Conference> {

	private static Logger logger = LoggerFactory.getLogger(ConferenceController.class);
	
	private static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");

	@Autowired
	private ConferenceDao conferenceDao;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public Conference findOne(@PathVariable BigInteger id) {
		Conference conference = super.findOne(id);
		for (Session session : conference.getSessions()) session.getSpeakers().size();
		return conference;
	}

	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public void conferences(
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			HttpServletResponse response) {

		if (year == null && month == null) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
		} else if (year == null || month == null) {
			throw new BadRequestParametersException("setting only parameter (year or month) is not permitted.");
		} else {
			if (year < 1900 || year > 2050) throw new BadRequestParametersException("parameter 'year' is out of range: " + year);
			if (month < 1 || month > 12)  throw new BadRequestParametersException("parameter 'month' is out of range: " + month);
		}
		
		List<Conference> conferences = conferenceDao.findByMonthAndYear(month, year);
		ObjectWriter writer = objectMapper.writerWithView(Object.class);
		
		try {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");
			
			writer.writeValue(response.getOutputStream(), conferences);
		} catch (IOException e) {
			logger.error("Cannot serialize conference list", e);
		}
	}
	
	@RequestMapping("/months")
	@ResponseBody
	public List<Long> months() {
		List<Conference> conferences = conferenceDao.findAll();
		Set<Long> dates = new HashSet<>();
		
		for (Conference conference : conferences) {			
			Calendar startCalendar = (Calendar) conference.getStartDate().clone();
			Calendar endCalendar = (Calendar) conference.getEndDate().clone();
			
			startCalendar.setTimeZone(UTC_TZ);
			endCalendar.setTimeZone(UTC_TZ);
			
			startCalendar.set(Calendar.DAY_OF_MONTH, 1);
			
			for (int field : new int[] {Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}) {
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

	@Override
	protected ConferenceDao getDao() {
		return conferenceDao;
	}
}
