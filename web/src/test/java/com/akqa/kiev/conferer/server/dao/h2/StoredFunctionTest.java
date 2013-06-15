package com.akqa.kiev.conferer.server.dao.h2;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.akqa.kiev.conferer.server.dao.jpa.h2.StoredFunction;

public class StoredFunctionTest {

	@Test
	public void happensInMonthYear_timezone_Asia() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date startDate = dateFormat.parse("31-12-2012 08:00:00.000");
		Date endDate = dateFormat.parse("31-12-2012 23:59:59.999");
		
		assertThat(checkFunction(1, 2013, startDate, endDate, "Asia/Tokyo"), is(true));
	}
	
	@Test
	public void happensInMonthYear_inner() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date startDate = dateFormat.parse("10-01-2013 08:00:00.000");
		Date endDate = dateFormat.parse("11-01-2013 18:00:00.000");
		
		assertThat(checkFunction(1, 2013, startDate, endDate, "UTC"), is(true));
	}
	
	@Test
	public void happensInMonthYear_outer() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date startDate = dateFormat.parse("31-10-2012 08:00:00.000");
		Date endDate = dateFormat.parse("01-04-2013 23:59:59.999");
		
		assertThat(checkFunction(1, 2013, startDate, endDate, "UTC"), is(true));
	}
	
	@Test
	public void happensInMonthYear_startsEarlier() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date startDate = dateFormat.parse("31-10-2012 08:00:00.000");
		Date endDate = dateFormat.parse("05-01-2013 23:59:59.999");
		
		assertThat(checkFunction(1, 2013, startDate, endDate, "UTC"), is(true));
	}
	
	@Test
	public void happensInMonthYear_finishesLater() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date startDate = dateFormat.parse("10-01-2013 08:00:00.000");
		Date endDate = dateFormat.parse("05-02-2013 23:59:59.999");
		
		assertThat(checkFunction(1, 2013, startDate, endDate, "UTC"), is(true));
	}
	
	@Test
	public void happensInMonthYear_outOfRange() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date startDate = dateFormat.parse("10-01-2012 08:00:00.000");
		Date endDate = dateFormat.parse("15-02-2012 23:59:59.999");
		
		assertThat(checkFunction(1, 2013, startDate, endDate, "UTC"), is(false));
	}
	
	protected Boolean checkFunction(int month, int year, Date eventStart, Date eventEnd, String timezoneId) {
		return StoredFunction.happensInMonthYear(month, year, eventStart, eventEnd, timezoneId);
	}
}
