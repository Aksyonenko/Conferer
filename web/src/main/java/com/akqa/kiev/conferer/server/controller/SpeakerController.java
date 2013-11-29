package com.akqa.kiev.conferer.server.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.model.Session;
import com.akqa.kiev.conferer.server.model.Speaker;

@Controller
@RequestMapping("/speakers")
public class SpeakerController extends AbstractConfererController<Speaker> {
    
    @RequestMapping(value = "/{id}/sessions", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Session> sessions(@PathVariable BigInteger id) {
        return speakerDao.findOne(id).getSessions();
    }

	@Autowired
	private SpeakerDao speakerDao;

	@Override
	protected SpeakerDao getDao() {
		return speakerDao;
	}
}
