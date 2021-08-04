package com.core.api.security.service.exceptions;

public class AuthorizationException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public AuthorizationException(String msg)
	{
		super(msg);
	}

	public AuthorizationException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
