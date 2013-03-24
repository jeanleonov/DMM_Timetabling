package com.rosinka.tt.server.base.exceptions;

/**
 * Internal system exception. Used within system only
 * and not intended to be transfered to client side
 */
public class InternalNotForUserException extends Exception
{
	private static final long serialVersionUID = -2190381095997055155L;
	
	public InternalNotForUserException(String message)
	{
		super(new Exception(message));
	}
	
	public InternalNotForUserException(Exception exception)
	{
		super(exception.getMessage());
	}
			
	public InternalNotForUserException(String message, Exception exception)
	{
		super(message, exception);
	}
}
