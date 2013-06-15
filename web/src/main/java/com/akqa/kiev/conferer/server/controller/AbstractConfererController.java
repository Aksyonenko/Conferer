package com.akqa.kiev.conferer.server.controller;

import java.math.BigInteger;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akqa.kiev.conferer.server.dao.AbstractDao;
import com.akqa.kiev.conferer.server.model.AbstractEntity;

public abstract class AbstractConfererController<T extends AbstractEntity> {

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional(readOnly = true)
	public T findOne(@PathVariable BigInteger id) {
		try {
			T entity = getDao().findOne(id);
			if (entity == null) throw new IncorrectResultSizeDataAccessException(1, 0);
			return entity;
			
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new ResourceNotFoundException("Conference " + id);
		}
	}
	
	protected abstract AbstractDao<T> getDao();
}
