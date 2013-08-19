package com.akqa.kiev.conferer.server.model;

import java.io.Serializable;

public class Image<ID extends Serializable> {
	
	private ID id;
	
	private String format;
	
	private byte[] data;
	
	public ID getId() {
		return id;
	}
	
	public void setId(ID id) {
		this.id = id;
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
}
