package com.akqa.kiev.conferer.server.dao.mongo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Field;

import com.mongodb.DBObject;

public class CustomSpringField extends Field {

	private Map<String, Object> projections = new HashMap<>();
	
	public void project(DBObject projection) {
		String field = projection.keySet().iterator().next();
		projections.put(field, projection.get(field));
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.mongodb.core.query.Field#getFieldsObject()
	 */
	@Override
	public DBObject getFieldsObject() {
		DBObject fields = super.getFieldsObject();
		
		for (String field : projections.keySet()) {
			if (fields.containsField(field)) fields.removeField(field);
			fields.put(field, projections.get(field));
		}
		
		return fields;
	}
	
	
}
