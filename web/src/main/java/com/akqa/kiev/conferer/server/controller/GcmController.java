
package com.akqa.kiev.conferer.server.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.server.dao.GcmRegistrationDao;
import com.akqa.kiev.conferer.server.gcm.GcmSender;
import com.akqa.kiev.conferer.server.model.GcmRegistration;

@Controller
@RequestMapping("/gcm")
public class GcmController {

    @Autowired
    private GcmRegistrationDao gcmRegistrationDao;

    @Autowired
    private GcmSender gcmSender;

    @RequestMapping(value = "/register/{id}", method = RequestMethod.GET)
    @ResponseBody
    public GcmRegistration register(@PathVariable String id) {
        GcmRegistration gcmRegistration = new GcmRegistration(id);
        gcmRegistrationDao.save(gcmRegistration);
        return gcmRegistration;
    }
    
    //TODO - remove later
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<GcmRegistration> findAll() {
        return gcmRegistrationDao.findAll();
    }

    @RequestMapping(value = "/unregister/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void unregister(@PathVariable String id) {
        if (gcmRegistrationDao.exists(id)) {
            gcmRegistrationDao.delete(id);
        }
    }

    @RequestMapping(value = "/notifyAll", method = RequestMethod.GET)
    @ResponseBody
    public void sendNotification() throws IOException {
        gcmSender.sendNotification();
    }

}
