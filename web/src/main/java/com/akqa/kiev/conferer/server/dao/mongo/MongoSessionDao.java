package com.akqa.kiev.conferer.server.dao.mongo;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Repository;

import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.model.Session;

@Repository
public class MongoSessionDao implements SessionDao {

	@Override
	public Session findOne(String id)
			throws IncorrectResultSizeDataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
