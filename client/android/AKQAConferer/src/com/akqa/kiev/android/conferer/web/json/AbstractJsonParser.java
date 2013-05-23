package com.akqa.kiev.android.conferer.web.json;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
					break;
				}
				if (!field.getType().equals(Date.class)) {
					clazz.getMethod(methodName, field.getType()).invoke(
							instance, value);
				} else {
					clazz.getMethod(methodName, field.getType()).invoke(
							instance, new Date((Long) value));
				}
			}
		} catch (Exception e) {
			throw new JsonParseException(e);
		}
		return instance;
	}

	protected List<T> parseJsonListObjects(JSONArray jsonArray)
			throws JsonParseException {
		List<T> listObjects = new ArrayList<T>(jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				Object jsonObject = jsonArray.get(i);
				T dataObject = parseJsonObject((JSONObject) jsonObject);
				listObjects.add(dataObject);
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