package com.akqa.kiev.conferer.server.dao.image;

import java.io.Serializable;

import com.akqa.kiev.conferer.server.model.Image;

public interface AbstractImageDao<ID extends Serializable> {

	void save(Image<ID> image);
	
	boolean exists(ID id);
	
	Image<ID> findOne(ID id);

	void delete(ID id);
}