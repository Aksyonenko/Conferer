package com.akqa.kiev.conferer.server.dao.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class IsoDateSerializer extends StdSerializer<Calendar> {

	public final static String ISO_DATE_FORMAT = "dd-MM-yyyy'T'HH:mm:ss.SSSZ"; 
	
	public IsoDateSerializer() {
		super(Calendar.class);
	}

	@Override
	public void serialize(Calendar value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
		DateFormat dateFormat = new SimpleDateFormat(ISO_DATE_FORMAT);
		dateFormat.setCalendar(value);
		
		jgen.writeString(dateFormat.format(value.getTime()));
	}

}
