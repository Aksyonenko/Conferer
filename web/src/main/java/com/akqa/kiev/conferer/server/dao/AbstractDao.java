package com.akqa.kiev.conferer.server.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;

import com.akqa.kiev.conferer.server.model.AbstractEntity;

public interface AbstractDao<T extends AbstractEntity> extends Repository<T, BigInteger>, QueryDslPredicateExecutor<T> {

	T findOne(BigInteger id) throws IncorrectResultSizeDataAccessException;
	List<T> findAll();
	
	void save(T entity);
}
