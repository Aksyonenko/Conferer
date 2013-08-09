package com.akqa.kiev.conferer.server.dao.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.akqa.kiev.conferer.server.dao.AbstractImageDao;
import com.akqa.kiev.conferer.server.dao.ImageDao;
import com.akqa.kiev.conferer.server.dao.ImageIdGenerator;

@Configuration
public class FileStoreConfig {

	@Bean
	public AbstractImageDao imageUploadHelper() {
		AbstractImageDao iuh = new ImageDao();
		return iuh;
	}
	
	@Bean
	public ImageIdGenerator imageIdGenerator() {
		ImageIdGenerator generator = new ImageIdGenerator();
		return generator;
	}
	
	static @Bean public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        Resource[] resourceLocations = new Resource[] {
                new ClassPathResource("storage.properties"),
        };
        configurer.setLocations(resourceLocations);
        return configurer;
	}
}
