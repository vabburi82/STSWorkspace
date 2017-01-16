package com.venu.springmvc.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class StudentAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { StudentAppRootConfiguration.class };
    }
  
    @Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { StudentAppWebConfig.class };
	}
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
 
}
