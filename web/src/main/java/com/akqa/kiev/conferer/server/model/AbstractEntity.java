package com.akqa.kiev.conferer.server.model;

import org.springframework.data.annotation.Id;

public abstract class AbstractEntity<T> {

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
