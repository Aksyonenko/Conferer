
package com.akqa.kiev.conferer.server;


import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.Conference;
import com.akqa.kiev.conferer.Speaker;

@Controller
public class ConferenceController {

    private static final TimeZone UTC_TZ = TimeZone.getTimeZone("UTC");

    @Autowired
    private ConferenceDao conferenceDao;

    @Autowired
    private SpeakerDao speakerDao;

    @RequestMapping(value = "/conferences", method = RequestMethod.GET)
    @ResponseBody
    public List<Conference> conferences(@RequestParam(value = "year", required = false) Integer year, @RequestParam(
            value = "month", required = false) Integer month) {
        Calendar startCalendar = Calendar.getInstance(UTC_TZ);

        if (year != null && month != null) {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, month - 1);
        }

        zeroCalendarTime(startCalendar);
        Calendar endCalendar = (Calendar) startCalendar.clone();
        endCalendar.add(Calendar.MONTH, 1);

        return conferenceDao.getConferences(startCalendar.getTime(), endCalendar.getTime());
    }

    @RequestMapping(value = "/speakers/{speakerId}", method = RequestMethod.GET)
    @ResponseBody
    public Speaker speakerDetails(@PathVariable("speakerId") String speakerId) {
        return speakerDao.getSpeakerById(speakerId);
    }

    private static void zeroCalendarTime(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

}
