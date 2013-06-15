package com.akqa.kiev.conferer.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -6606834910584249101L;

	protected ResourceNotFoundException() {
		super();
	}

	protected ResourceNotFoundException(String resource) {
		super("Unable to find [" + resource + "]");
	}
	
	
}