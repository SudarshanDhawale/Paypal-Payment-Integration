package com.cpt.payments.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class HmacAuthenticationToken extends AbstractAuthenticationToken{


	private static final long serialVersionUID = 1L;

	private final Object credentials;

	private final Object principal;
	
	public HmacAuthenticationToken(Object credentials, Object principal) {
		super(null);
		this.credentials = credentials;
        this.principal = principal;
        setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return principal;
	}

}
