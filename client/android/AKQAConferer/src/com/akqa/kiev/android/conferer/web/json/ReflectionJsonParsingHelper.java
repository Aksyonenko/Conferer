package com.akqa.kiev.android.conferer.web.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Helper class for parsing json to model classes.
 * 
 * @author Yuriy.Belelya
 * 
 */
public final class ReflectionJsonParsingHelper {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd-MM-yyyy'T'hh:mm:ss.SSS", Locale.ENGLISH);

	private ReflectionJsonParsingHelper() {

	}

	public static <T> T objectFromJsonString(String jsonString, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			JSONException, InvocationTargetException, NoSuchMethodException,
			ParseException {

		JSONObject jsonObject = new JSONObject(jsonString);
		return objectFromJsonObject(clazz, jsonObject);
	}

	public static <T> List<T> listObjectsFromJsonString(String jsonString,
			Class<T> clazz) throws InstantiationException,
			IllegalAccessException, JSONException, InvocationTargetException,
			NoSuchMethodException, ParseException {

		JSONArray jsonArray = new JSONArray(jsonString);
		return listObjectsFromJsonArray(clazz, jsonArray);
	}

	public static <T> T objectFromJsonObject(Class<T> clazz,
			JSONObject jsonObject) throws InstantiationException,
			IllegalAccessException, JSONException, InvocationTargetException,
			NoSuchMethodException, ParseException {

		T object = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		if (!clazz.getSuperclass().equals(Object.class)) {
			Field[] superClassFields = clazz.getSuperclass().getDeclaredFields();
			fields = addAll(fields, superClassFields);
		}
		for (Field field : fields) {
			if (jsonObject.has(field.getName())) {
				Class<?> type = field.getType();
				String fieldName = field.getName();
				Object value = jsonObject.get(fieldName);
				String methodName = "set" + capitalize(fieldName);
				if (value == null || value.toString().equals("null")) {
					continue;
				}
				if (type.equals(Boolean.class)) {
					clazz.getMethod(methodName, type).invoke(object,
							jsonObject.getBoolean(fieldName));
				} else if (type.equals(Integer.class)) {
					clazz.getMethod(methodName, type).invoke(object,
							jsonObject.getInt(fieldName));
				} else if (type.equals(Long.class)) {
					clazz.getMethod(methodName, type).invoke(object,
							jsonObject.getLong(fieldName));
				} else if (type.equals(String.class)) {
					clazz.getMethod(methodName, type).invoke(object,
							value);
				} else if (type.equals(Date.class)) {
					if (value instanceof String) {
						clazz.getMethod(methodName, type).invoke(
								object, DATE_FORMAT.parse((String) value));
					} else {
						clazz.getMethod(methodName, field.getType()).invoke(
								object, new Date((Long) value));
					}

				} else if (field.getType().isAssignableFrom(List.class)) {
					ParameterizedType listType = (ParameterizedType) field
							.getGenericType();
					Class<?> listClass = (Class<?>) listType
							.getActualTypeArguments()[0];
					List<?> list = listObjectsFromJsonArray(listClass,
							jsonObject.getJSONArray(fieldName));
					clazz.getMethod(methodName, type).invoke(object,
							list);
				} else {
					clazz.getMethod(methodName, field.getType()).invoke(
							object,
							objectFromJsonObject(field.getType(),
									jsonObject.getJSONObject(field.getName())));
				}
			}
		}

		return object;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> listObjectsFromJsonArray(Class<T> clazz,
			JSONArray jsonArray) throws JSONException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, ParseException {

		int size = jsonArray.length();
		ArrayList<T> list = new ArrayList<T>(size);
		for (int i = 0; i < size; i++) {
			Object listObject = jsonArray.get(i);
			T object = null;
			if (listObject instanceof JSONObject) {
				object = objectFromJsonObject(clazz, (JSONObject) listObject);
			} else if (listObject.getClass().equals(clazz)) {
				object = (T) listObject;
			}
			list.add(object);
		}
		return list;
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
	private static<T> T[] addAll(T[] array1, T[] array2) {
		if (array1 == null) {
			return cloneArray(array2);
		} else if (array2 == null) {
		}
		T[] joinedArray = (T[]) Array.newInstance(array1.getClass()
				.getComponentType(), array1.length + array2.length);
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}
	
	private static<T> T[] cloneArray(T[] array) {
		if (array == null) {
			return null;
		}
		return (T[]) array.clone();
	}   

}