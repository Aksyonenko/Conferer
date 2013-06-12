package com.akqa.kiev.conferer.server.dao;

import java.math.BigInteger;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.Repository;

import com.akqa.kiev.conferer.server.model.AbstractEntity;

public interface AbstractDao<T extends AbstractEntity> extends Repository<T, BigInteger> {

	T findOne(BigInteger id) throws IncorrectResultSizeDataAccessException;
	
}
