package com.akqa.kiev.conferer.server.model;

import java.math.BigInteger;

public class Image {
	
	private BigInteger id;
	
	private String format;
	
	private byte[] data;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
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
