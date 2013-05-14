package com.akqa.kiev.conferer.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.model.Speaker;

@Controller
@RequestMapping("/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerDao speakerDao;
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Speaker speakerDetails(@PathVariable("id") String id) {
        return speakerDao.findOne(id);
    }
}
