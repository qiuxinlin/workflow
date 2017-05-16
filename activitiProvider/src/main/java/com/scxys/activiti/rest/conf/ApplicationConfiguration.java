package com.scxys.activiti.rest.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
  @PropertySource(value = "classpath:db.properties", ignoreResourceNotFound = true),
  @PropertySource(value = "classpath:engine.properties", ignoreResourceNotFound = true)
})
@ComponentScan(basePackages = {"com.scxys.activiti.rest.conf"})
@ImportResource({"classpath:activiti-custom-context.xml"})
public class ApplicationConfiguration {
  
}
