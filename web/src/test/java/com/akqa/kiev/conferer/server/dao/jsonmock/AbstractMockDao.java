package com.akqa.kiev.conferer.server.dao.jsonmock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;

import com.akqa.kiev.conferer.server.dao.AbstractDao;
import com.akqa.kiev.conferer.server.model.AbstractEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile("json-mock")
public abstract class AbstractMockDao<T extends AbstractEntity<T>> implements AbstractDao<T> {
	
	protected final Map<String, T> map = new HashMap<>();
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	protected Resource jsonResource;
			
	protected void setEntities(Collection<T> entites) {
		for (T entity : entites) {
			map.put(entity.getId(), entity);
		}
	}

	/* (non-Javadoc)
	 * @see com.akqa.kiev.conferer.server.dao.AbstractDao#findOne(java.lang.String)
	 */
	@Override
	public T findOne(String id) throws IncorrectResultSizeDataAccessException {
		return map.get(id);
	}
}
