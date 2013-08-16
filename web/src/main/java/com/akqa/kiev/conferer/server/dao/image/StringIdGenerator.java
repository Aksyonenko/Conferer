package com.akqa.kiev.conferer.server.dao.image;

import org.springframework.stereotype.Component;

import com.akqa.kiev.conferer.server.model.AbstractEntity;

@Component
public class StringIdGenerator implements AbstractIdGenerator<String> {

	@Override
	public String generate(AbstractEntity entity) {
		StringBuilder idBuilder = new StringBuilder(entity.getClass()
				.getSimpleName());
		idBuilder.append(entity.getId().toString());
		return idBuilder.toString();
	}
}
