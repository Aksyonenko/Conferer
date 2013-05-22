package com.akqa.kiev.conferer.server.dao.mongo;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.DBObject;

public class CustomSpringQuery extends Query {

	private CustomSpringField fieldSpec;
	
	public CustomSpringQuery(Criteria criteria) {
		super(criteria);
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.mongodb.core.query.Query#fields()
	 */
	@Override
	public CustomSpringField fields() {
		if (fieldSpec == null) fieldSpec = new CustomSpringField();
		return fieldSpec;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.mongodb.core.query.Query#getFieldsObject()
	 */
	@Override
	public DBObject getFieldsObject() {
		return (fieldSpec == null) ? null : fieldSpec.getFieldsObject();
	}
	
}
