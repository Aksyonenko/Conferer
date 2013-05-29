package com.akqa.kiev.conferer.server.dao.json;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class IsoDateSerializer extends StdSerializer<Date> {

	public IsoDateSerializer() {
		super(Date.class);
	}

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
//		jgen.g
	}

}
