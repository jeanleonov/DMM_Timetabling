package com.timetabling.server.base.exceptions;

import com.timetabling.shared.ConstantsShared;



/**
 * Holds a list of registered system exceptions 
 */
public enum ExceptionType
{
	UnknownEntity(2,"There is no entity with a given ID"),	
	UnknownError(3,"The error has occured"),
	DatabaseError(4,"The error has occured during DB quering");
	
	private int code;
	
	private String explanation;
	
	private ExceptionType(int code, String explantaion)
	{
		this.code = code;
		
		this.explanation = explantaion;
	}

	public void setExplanation(String explanation)
	{
		this.explanation = explanation;
	}

	public String getExplanation()
	{
		return explanation;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}
	
	/**
	 * Forms string representation of exception to transfer it to client
	 * @return string representation of exception
	 */
	public String getStringRepresentation()
	{
		StringBuilder exceptionMessage = new StringBuilder("Exception:");
		
		exceptionMessage.append(this.getCode()).append(ConstantsShared.SEPARATOR).append(this.getExplanation()).append(ConstantsShared.SEPARATOR);
		
		return exceptionMessage.toString();
	}
}
