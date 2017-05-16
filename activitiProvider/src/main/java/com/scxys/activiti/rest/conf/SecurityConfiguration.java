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
     .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
     .csrf().disable()
     .authorizeRequests()
       .anyRequest().authenticated()
       .and()
     .httpBasic();
  }*/
}
