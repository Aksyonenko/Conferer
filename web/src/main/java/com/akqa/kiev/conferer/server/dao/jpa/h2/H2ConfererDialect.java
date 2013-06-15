package com.akqa.kiev.conferer.server.dao.jpa.h2;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H2ConfererDialect extends H2Dialect {

	private static final Logger log = LoggerFactory.getLogger(H2ConfererDialect.class);
	
	public H2ConfererDialect() {
		super();
		log.info("Registering Hibernate function of " + StoredFunction.SQL_NAME.toLowerCase());
		registerFunction(StoredFunction.SQL_NAME.toLowerCase(), new StandardSQLFunction(StoredFunction.SQL_NAME, StandardBasicTypes.BOOLEAN));
	}

}
