package com.akqa.kiev.conferer.server.dao;

import java.math.BigInteger;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;

public class ImageIdGenerator {

	@Value("${categories}")
	private String categories;

	public BigInteger generate(String category, BigInteger entityId) {
		return new BigInteger(getCategories().getProperty(
				category.toLowerCase()).concat(entityId.toString()));
	}

	// TODO azeltinsh
	public Properties getCategories() {
		Properties props = new Properties();
		if (categories.length() != 0) {
			String[] tokens = categories.split(",");
			for (String token : tokens) {
				String[] pairs = token.split("=");
				props.put(pairs[0].trim().toLowerCase(), pairs[1].trim()
						.toLowerCase());
			}
		}

		return props;
	}
}
