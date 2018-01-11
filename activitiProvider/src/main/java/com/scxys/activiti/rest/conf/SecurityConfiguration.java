package com.scxys.activiti.rest.conf;

/*
@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity*/
public class SecurityConfiguration /*extends WebSecurityConfigurerAdapter*/ {
  
  /*@Bean
  public AuthenticationProvider authenticationProvider() {
    return new BasicAuthenticationProvider();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
  	 http
     .authenticationProvider(authenticationProvider())
     // 基于token，所以不需要session
     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
     // 由于使用的是JWT，不需要csrf
     .csrf().disable()
     .authorizeRequests()
     // 所有请求全部需要鉴权认证
       .anyRequest().authenticated()
       .and()
     .httpBasic();
  }*/
}
