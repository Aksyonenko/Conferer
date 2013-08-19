package com.akqa.kiev.conferer.server.dao.zk;

import org.zkoss.bind.annotation.Init;
import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.model.Conference;

public class ConferenceViewModel extends AbstractEntityListModel<Conference> {

    public ConferenceViewModel() {
        super(ConferenceDao.class);
    }

    @Override
    @Init
    public void init() {
        super.init();
    }

}
