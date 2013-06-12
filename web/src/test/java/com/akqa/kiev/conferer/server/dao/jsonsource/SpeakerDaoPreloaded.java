package com.akqa.kiev.conferer.server.dao.jsonsource;

import java.math.BigInteger;
import java.util.Map;

import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.model.Speaker;

public class SpeakerDaoPreloaded extends AbstractDaoPreloaded<Speaker> implements SpeakerDao {

	public SpeakerDaoPreloaded(Map<BigInteger, Speaker> map) {
		super(map);
	}

}
