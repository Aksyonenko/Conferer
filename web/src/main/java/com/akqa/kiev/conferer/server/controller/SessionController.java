package com.akqa.kiev.conferer.server.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.model.Session;


@Controller
@RequestMapping("/sessions")
public class SessionController {

	@Autowired
	private SessionDao sessionDao;
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Session speakerDetails(@PathVariable("id") BigInteger id) {
        return sessionDao.findOne(id);
    }
}
