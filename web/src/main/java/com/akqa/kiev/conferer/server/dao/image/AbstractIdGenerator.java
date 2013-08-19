package com.akqa.kiev.conferer.server.dao.image;

import java.io.Serializable;

import com.akqa.kiev.conferer.server.model.AbstractEntity;

public interface AbstractIdGenerator<ID extends Serializable> {

	ID generate(AbstractEntity entity);
}
