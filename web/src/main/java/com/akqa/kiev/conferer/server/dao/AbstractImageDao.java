package com.akqa.kiev.conferer.server.dao;

import java.math.BigInteger;

import com.akqa.kiev.conferer.server.model.Image;

public interface AbstractImageDao {

	void save(Image image);
	
	boolean exists(BigInteger id);
	
	Image findOne(BigInteger id);

	void delete(BigInteger id);
}