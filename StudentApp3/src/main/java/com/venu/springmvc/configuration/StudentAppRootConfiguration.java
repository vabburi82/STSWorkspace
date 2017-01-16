package com.venu.springmvc.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.venu.springmvc.dao")
public class StudentAppRootConfiguration {

}