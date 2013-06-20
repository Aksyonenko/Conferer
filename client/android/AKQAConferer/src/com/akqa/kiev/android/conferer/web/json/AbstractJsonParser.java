package com.akqa.kiev.android.conferer.web.json;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.akqa.kiev.android.conferer.web.json.exception.JsonParseException;

/**
 * Abstract json parser.
 * 
 * @author Yuriy.Belelya
 * 
 * @param <T>
 *            - entity class that mapped to json.
 */
public abstract class AbstractJsonParser<T> {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd-MM-yyyy'T'hh:mm:ss.SSS", Locale.ENGLISH);


	protected T parseJsonObject(JSONObject jsonObject)
			throws JsonParseException {
		T instance = null;
		Class<T> clazz = getGenericClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			instance = clazz.newInstance();
			for (Field field : fields) {
				String fieldName = field.getName();
				String methodName = "set" + capitalize(fieldName);
				Object value = jsonObject.get(fieldName);
				if (value == null || value.toString().equals("null")) {
					continue;
				}
				if (!field.getType().equals(Date.class)) {
					clazz.getMethod(methodName, field.getType()).invoke(
							instance, value);
				} else {
					if (value instanceof String) {
						clazz.getMethod(methodName, field.getType()).invoke(
								instance, DATE_FORMAT.parse((String) value));
					} else {
						clazz.getMethod(methodName, field.getType()).invoke(
								instance, new Date((Long) value));
					}
				}
			}
		} catch (Exception e) {
			throw new JsonParseException(e);
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	protected List<T> parseJsonListObjects(JSONArray jsonArray)
			throws JsonParseException {
		List<T> listObjects = new ArrayList<T>(jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				Object object = jsonArray.get(i);
				if (object instanceof JSONObject) {
					T dataObject = parseJsonObject((JSONObject) object);
					listObjects.add(dataObject);
				} else {
					listObjects.add((T) object);
				}
			} catch (Exception e) {
				throw new JsonParseException(e);
			}
		}
		return listObjects;
	}

	private static String capitalize(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		return new StringBuilder(str.length())
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getGenericClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

}