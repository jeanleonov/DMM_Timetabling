package com.timetabling.server.base.exceptions;

/**
 * Internal system exception. Used within system only
 * and not intended to be transfered to client side
 */
public class InternalException extends Exception
{
	private static final long serialVersionUID = -2190381095997055155L;
	
	private final ExceptionType exceptionType;
	
	public InternalException(ExceptionType exceptionType)
	{
		this.exceptionType = exceptionType;
	}
	
	public ExceptionType getExceptionType()
	{
		return exceptionType;
	}
	
	public InternalException(String message)
	{
		super(message);
		
		exceptionType = ExceptionType.UnknownEntity;
	}	
}
