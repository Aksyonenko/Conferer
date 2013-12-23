package com.akqa.kiev.conferer.server.controller;

import javax.servlet.http.HttpServletRequest;

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
    protected Session saveEntity(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    } 
	   
	@Override
	protected AbstractDao<Session> getDao() {
		return sessionDao;
	}
}
