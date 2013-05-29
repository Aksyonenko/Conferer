package com.akqa.kiev.conferer.server.dao.jsonsource;

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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;

import com.akqa.kiev.conferer.server.dao.ConferenceDao;
import com.akqa.kiev.conferer.server.model.Conference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConferenceDaoPreloaded extends AbstractDaoPreloaded<Conference> implements ConferenceDao {

	@Autowired
	private ObjectMapper objectMapper;
	
	private final Set<String> findAllFields = new HashSet<>();
	private boolean includeFields;
	
	public ConferenceDaoPreloaded(Map<String, Conference> map) {
		super(map);
	}

	
	@Override
	public List<Conference> find(Date fromDate, Date toDate) {
		List<Conference> list = new ArrayList<>();
		
		long from = fromDate.getTime();
		long to = toDate.getTime();
		
		for (Conference conference : map.values()) {
			long start = conference.getStartDate().getTime();
			long end = conference.getEndDate().getTime();
			
			boolean inner = (start >= from) && (end <= to);
			boolean outer = (start <= from) && (end >= to);
			boolean finishing = (start <= from) && (end >= from) && (end <= to);
			boolean beginning = (start >= from) && (start <= from) && (end >= to);
			
			if (inner || outer || finishing || beginning) list.add(conference); 
		}
		
		return reduceFields(list);
	}

	@Override
	public List<Conference> findAll() {
		return reduceFields(map.values());
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
	
	private List<Conference> reduceFields(Collection<Conference> originalConferences) {
		List<Conference> conferences = new ArrayList<>(originalConferences.size());
		
		for (Conference originalConference : originalConferences) {
			Conference conference = new Conference();
			conferences.add(conference);
			
			BeanUtils.copyProperties(originalConference, conference);
			BeanWrapper wrapper = new BeanWrapperImpl(conference);

			if (includeFields) {
				for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
					String property = descriptor.getName();
					if (property.equals("class") || property.equals("id")) continue;

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
}
