package com.akqa.kiev.conferer.server.dao;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.Repository;

import com.akqa.kiev.conferer.server.model.AbstractEntity;

public interface AbstractDao<T extends AbstractEntity<T>> extends Repository<T, String> {

	T findOne(String id) throws IncorrectResultSizeDataAccessException;
	
}
