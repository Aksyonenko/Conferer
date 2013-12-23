package com.akqa.kiev.conferer.server;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.akqa.kiev.conferer.server.dao.json.IsoDateSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.android.gcm.server.Sender;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.akqa.kiev.conferer.server.controller", "com.akqa.kiev.conferer.server.gcm",
        "com.akqa.kiev.conferer.server.dao.config"})
@PropertySource(value = { "classpath:project.properties" })
public class WebConfig extends WebMvcConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(WebConfig.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
    Environment env;
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setObjectMapper(objectMapper());
		jsonConverter.setPrettyPrint(true);
		
		converters.add(jsonConverter);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		Map<String, WebRequestInterceptor> map = context.getBeansOfType(WebRequestInterceptor.class);
		for (String beanId : map.keySet()) {
			log.debug("Registering custom interceptor '{}'", beanId);
			registry.addWebRequestInterceptor(map.get(beanId));
		}
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		SimpleModule calendarModule = new SimpleModule("CalendarSerialization");
		calendarModule.addSerializer(new IsoDateSerializer());
		mapper.registerModule(calendarModule);
		
		return mapper;
	}
	
	@Bean
	public Sender sender(){
	    return new Sender(env.getProperty("gcm.api.key"));
	}
}
