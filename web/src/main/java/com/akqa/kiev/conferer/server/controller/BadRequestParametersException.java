package com.akqa.kiev.conferer.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public final class BadRequestParametersException extends RuntimeException {
	private static final long serialVersionUID = -6606834910584249101L;

	protected BadRequestParametersException(String description) {
		super("Bad parameter: " + description);
	}
	
	protected BadRequestParametersException(String parameterName, Object value) {
		super(parameterName + " = " + value);
	}
}