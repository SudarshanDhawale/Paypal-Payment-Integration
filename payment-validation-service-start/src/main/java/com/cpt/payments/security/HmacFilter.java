package com.cpt.payments.security;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.service.impl.HMacSHA256Impl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HmacFilter extends OncePerRequestFilter {
	
	Logger logger = Logger.getLogger(getClass().getName());
	
	private Gson gson;
	
	private final HMacSHA256Impl hMacSHA256Impl;
	
	HmacFilter(Gson gson, HMacSHA256Impl hMacSHA256Impl){
		this.hMacSHA256Impl = hMacSHA256Impl;
		this.gson = gson;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String receivedHmacSignature = request.getHeader("HmacSignature");
		
		if(receivedHmacSignature == null) {
			
			throw new ValidationException(
					ErrorCodeEnum.MISSING_HMAC_SIGNATURE.getErrorCode(),
					ErrorCodeEnum.MISSING_HMAC_SIGNATURE.getErrorMessage(),
					HttpStatus.BAD_REQUEST
				);
		}
		
		WrappedRequest wrappedRequest = new WrappedRequest(request);
		
	    // Parse the string into a JsonObject using Gson
		String data = request.getRequestURI();
		
		if(wrappedRequest.getBody() != null && !wrappedRequest.getBody().isEmpty()) {
			data = data + "|" + getNormalizedJson(wrappedRequest.getBody());
		}
		
		logger.info("HmacFilter:: Data: " + data);
		
		// HMAX logic
		
//		logger.info("HmacFilter:: receivedHmacSignature: " + receivedHmacSignature);
		logger.info("HmacFilter:: Sending data for sign processing data: " + data);
		
		boolean isValid = hMacSHA256Impl.verifyHMAC(data, receivedHmacSignature);
		
		if(isValid) {
			System.out.println("HmacFilter: Signature is Valid calling next filter");
			
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			Authentication authentication =
			    new HmacAuthenticationToken("ECOM", "");
			context.setAuthentication(authentication);

			SecurityContextHolder.setContext(context);
			
			filterChain.doFilter(wrappedRequest, response);
		} else {
			
			System.out.println("HmacFilter: NOT valid sending 401");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
	public String getNormalizedJson(String rawJson) {
		
	    // Parse the raw JSON string
	    JsonElement jsonElement = JsonParser.parseString(rawJson);
	    // Convert it back to a JSON string
	    return gson.toJson(jsonElement);
	}


}
