package com.cpt.payments.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

import com.cpt.payments.exception.ExceptionHandlerFilter;
import com.cpt.payments.security.HmacFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	private HmacFilter hmacFilter;
	
	ExceptionHandlerFilter exceptionHandlerFilter;
	
	SecurityConfiguration(HmacFilter hmacFilter, ExceptionHandlerFilter exceptionHandlerFilter){
		this.hmacFilter = hmacFilter;
		this.exceptionHandlerFilter = exceptionHandlerFilter;
	}
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
	    .csrf(csrf -> csrf.disable())
	    
	    .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
	    
	    .addFilterBefore(exceptionHandlerFilter, DisableEncodeUrlFilter.class)
	    .addFilterBefore(hmacFilter, AuthorizationFilter.class)
		
	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}

