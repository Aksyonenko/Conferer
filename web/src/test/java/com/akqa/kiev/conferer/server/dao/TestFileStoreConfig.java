package com.akqa.kiev.conferer.server.dao;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.akqa.kiev.conferer.server.dao.AbstractImageDao;
import com.akqa.kiev.conferer.server.dao.ImageDao;

@Configuration
public class TestFileStoreConfig {

	@Bean
	public AbstractImageDao imageDao() {
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
                new ClassPathResource("test-storage.properties"),
        };
        configurer.setLocations(resourceLocations);
        return configurer;
	}
}
