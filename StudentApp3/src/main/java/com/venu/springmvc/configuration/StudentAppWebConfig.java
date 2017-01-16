package com.venu.springmvc.configuration;
import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc //<mvc:annotation-driven />
@Configuration
@ComponentScan(basePackages = "com.venu.springmvc.controller")
@EnableSwagger2
public class StudentAppWebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
    public Docket apiDoc() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
//          .pathMapping("/api")
          .directModelSubstitute(LocalDate.class, String.class)
          .genericModelSubstitutes(ResponseEntity.class)
          .apiInfo(apiInfo())
          ;                                           
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	private ApiInfo apiInfo() {
	    ApiInfo apiInfo = new ApiInfo(
	      "Students App REST API",
	      "Students App description of API.",
	      "API TOS",
	      "Terms of service",
	      new Contact("Venu","google.com","venu.abburi@company.com"),
	      "License of API",
	      "API license URL");
	    return apiInfo;
	}
}