package com.akqa.kiev.conferer.server.dao.zk;

import org.zkoss.bind.annotation.Init;

import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.model.Speaker;

public class SpeakerViewModel extends AbstractEntityListModel<Speaker> {
	
	public SpeakerViewModel() {
		super(SpeakerDao.class);
	}

	@Override
	@Init
	public void init() {
		super.init();
	}

}
