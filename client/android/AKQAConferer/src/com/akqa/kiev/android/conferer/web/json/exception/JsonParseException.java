package com.akqa.kiev.android.conferer.web.json.exception;

/**
 * Exception in case of incorrect json from server.
 * @author Yuriy.Belelya
 * 
 */
public class JsonParseException extends Exception {

	private static final long serialVersionUID = -5872269953357362064L;

	public JsonParseException() {
		super();
	}

	public JsonParseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public JsonParseException(String detailMessage) {
		super(detailMessage);
	}

	public JsonParseException(Throwable throwable) {
		super(throwable);
	}

}
