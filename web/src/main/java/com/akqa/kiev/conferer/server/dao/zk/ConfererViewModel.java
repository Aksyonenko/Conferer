package com.akqa.kiev.conferer.server.dao.zk;

import org.zkoss.bind.annotation.Init;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.model.Conference;

@Init(superclass=true)
public class ConfererViewModel extends AbstractEntityListModel<Conference> {
	private static final long serialVersionUID = 7502437098525028472L;
	
	public ConfererViewModel() {
		super(ConferenceDao.class);
	}
}
