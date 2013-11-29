package com.akqa.kiev.conferer.server.dao;

import java.math.BigInteger;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.akqa.kiev.conferer.server.model.AbstractEntity;

@Transactional(readOnly = true)
public interface AbstractDao<T extends AbstractEntity> extends CrudRepository<T, BigInteger>, QueryDslPredicateExecutor<T> {

	//T findOne(BigInteger id) throws IncorrectResultSizeDataAccessException;
	//List<T> findAll();
	
	//T save(T entity);
}
