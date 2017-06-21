package com.scxys.activiti.rest.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scxys.activiti.rest.common.application.ContentTypeResolver;
import com.scxys.activiti.rest.common.application.DefaultContentTypeResolver;
import com.scxys.activiti.rest.service.api.RestResponseFactory;

/**
 * @author Joram Barrez
 */
@Configuration
public class RestConfiguration {

    @Bean()
    public RestResponseFactory restResponseFactory() {
      RestResponseFactory restResponseFactory = new RestResponseFactory();
      return restResponseFactory;
    }

    @Bean()
    public ContentTypeResolver contentTypeResolver() {
      ContentTypeResolver resolver = new DefaultContentTypeResolver();
      return resolver;
    }
}
