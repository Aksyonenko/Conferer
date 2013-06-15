package com.akqa.kiev.conferer.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.model.Speaker;

@Controller
@RequestMapping("/speakers")
public class SpeakerController extends AbstractConfererController<Speaker> {

	@Autowired
	private SpeakerDao speakerDao;

	@Override
	protected SpeakerDao getDao() {
		return speakerDao;
	}
}
