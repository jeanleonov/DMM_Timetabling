package com.timetabling.server.base.exceptions;

import javax.servlet.ServletException;

public class TaskServiceException extends ServletException
{
    private static final long serialVersionUID = 1962796823116764021L;

    public TaskServiceException( ExceptionType exceptionType )
    {
        super( exceptionType.getStringRepresentation() );
    }
}
