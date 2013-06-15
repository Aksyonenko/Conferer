package com.akqa.kiev.conferer.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akqa.kiev.conferer.server.dao.AbstractDao;
import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.model.Session;


@Controller
@RequestMapping("/sessions")
public class SessionController extends AbstractConfererController<Session> {

	@Autowired
	private SessionDao sessionDao;

	@Override
	protected AbstractDao<Session> getDao() {
		return sessionDao;
	}
}
