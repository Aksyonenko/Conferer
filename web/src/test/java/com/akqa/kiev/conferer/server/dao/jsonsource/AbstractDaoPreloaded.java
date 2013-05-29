package com.akqa.kiev.conferer.server.dao.jsonsource;

import java.util.Map;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;

import com.akqa.kiev.conferer.server.dao.AbstractDao;
import com.akqa.kiev.conferer.server.model.AbstractEntity;

@Component
public abstract class AbstractDaoPreloaded<T extends AbstractEntity> implements AbstractDao<T> {
	
	protected final Map<String, T> map;
	
	protected AbstractDaoPreloaded(Map<String, T> map) {
		this.map = map;
	}

	/* (non-Javadoc)
	 * @see com.akqa.kiev.conferer.server.dao.AbstractDao#findOne(java.lang.String)
	 */
	@Override
	public T findOne(String id) throws IncorrectResultSizeDataAccessException {
		return map.get(id);
	}
}
