package com.akqa.kiev.conferer.server.dao.jsonmock;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.mongodb.repository.Query;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.model.Conference;
import com.akqa.kiev.conferer.server.model.Session;
import com.akqa.kiev.conferer.server.model.Speaker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.jayway.jsonpath.JsonPath;

public class MockConferenceDao extends AbstractMockDao<Conference> implements
		ConferenceDao {

	private final Set<String> findAllFields = new HashSet<>();
	private boolean includeFields;
	
	@Override
	public List<Conference> find(Date fromDate, Date toDate) {
		List<Conference> list = new ArrayList<>();
		
		long from = fromDate.getTime();
		long to = fromDate.getTime();
		
		for (Conference conference : map.values()) {
			long start = conference.getStartDate().getTime();
			long end = conference.getEndDate().getTime();
			
			boolean inner = (start >= from) && (end <= to);
			boolean outer = (start <= from) && (end >= to);
			boolean finishing = (start <= from) && (end >= from) && (end <= to);
			boolean beginning = (start >= from) && (start <= from) && (end >= to);
			
			if (inner || outer || finishing || beginning) list.add(conference); 
		}
		
		return list;
	}

	@Override
	public List<Conference> findAll() {
		List<Conference> conferences = new ArrayList<>(map.values().size());

		for (Conference conference : map.values()) {
			// conference = conference.clone();
			BeanWrapper wrapper = new BeanWrapperImpl(conference);

			if (includeFields) {
				for (PropertyDescriptor descriptor : wrapper
						.getPropertyDescriptors()) {
					String property = descriptor.getName();
					if (property.equals("class") || property.equals("id"))
						continue;

					if (!findAllFields.contains(property)) {
						wrapper.setPropertyValue(property, null);
					}
				}
			} else {
				for (String field : findAllFields) {
					wrapper.setPropertyValue(field, null);
				}
			}
		}

		return conferences;
	}

	@PostConstruct
	public void configureFieldSetFromAnnotation() {
		try {
			Method findAllMethod = ConferenceDao.class.getMethod("findAll");
			Query annotation = findAllMethod.getAnnotation(Query.class);

			String fields = (annotation != null) ? annotation.fields() : null;
			if (fields != null && !fields.isEmpty()) {
				fields = fields.replace('\'', '"');
				
				@SuppressWarnings("unchecked")
				Map<String, Integer> result = objectMapper.readValue(fields, HashMap.class);

				includeFields = (result.values().iterator().next() == 0) ? false : true;
				for (String field : result.keySet()) {
					findAllFields.add(field);
				}
			}

		} catch (SecurityException | IOException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

}
