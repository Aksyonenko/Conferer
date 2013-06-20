package com.akqa.kiev.conferer.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

public class WebInitializer implements WebApplicationInitializer {
	
	private static final Logger logger = LoggerFactory.getLogger(WebApplicationInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		logger.info("Initializating Conferer server application");
		
		servletContext.setInitParameter("spring.profiles.default", "local");
		
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class);
		
		servletContext.addListener(new ContextLoaderListener(context));
		
		ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
		
		initZK(servletContext);
	}
	
	private void initZK(ServletContext servletContext) {
		ServletRegistration.Dynamic zkLoader = servletContext.addServlet("zkLoader", DHtmlLayoutServlet.class);
		zkLoader.addMapping("*.zul");
		zkLoader.setInitParameter("update-uri", "/zkau");
		zkLoader.setLoadOnStartup(1);
		
		ServletRegistration.Dynamic auEngine = servletContext.addServlet("auEngine", DHtmlUpdateServlet.class);
		auEngine.addMapping("/zkau/*");
	}

}

