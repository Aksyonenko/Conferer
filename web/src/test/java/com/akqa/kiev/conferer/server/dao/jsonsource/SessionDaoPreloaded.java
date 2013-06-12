package com.akqa.kiev.conferer.server.dao.jsonsource;

import java.math.BigInteger;
import java.util.Map;

import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.model.Session;

public class SessionDaoPreloaded extends AbstractDaoPreloaded<Session> implements SessionDao {

	public SessionDaoPreloaded(Map<BigInteger, Session> map) {
		super(map);
	}

}
