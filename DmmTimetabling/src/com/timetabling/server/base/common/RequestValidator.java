package com.timetabling.server.base.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.timetabling.shared.ConstantsShared;


public class RequestValidator
{
    Logger logger = Logger.getLogger( RequestValidator.class.getName() );

    private final Map<String, String[]> parameterMap;

    public RequestValidator( Map<String, String[]> parameterMap ) throws Exception
    {
    	if(null == parameterMap)
    		throw new Exception("Request parameter map is empty");
    	
        this.parameterMap = new TreeMap<String, String[]>(String.CASE_INSENSITIVE_ORDER);
        
        this.parameterMap.putAll(parameterMap);
    }

    public boolean containsKey( String parameterKey )
    {
        return ( parameterMap.containsKey( parameterKey ) && null !=  parameterMap.get( parameterKey ) );
    }

    public int getInt( String parameterKey ) throws Exception
    {
        return getInt( parameterKey, false );
    }

    public float getFloat( String parameterKey ) throws Exception
    {
        return getFloat( parameterKey, false );
    }
    
    public String getString( String parameterKey ) throws Exception
    {
        return getString( parameterKey, false );
    }

    public boolean getBoolean( String parameterKey ) throws Exception
    {
        return getBoolean( parameterKey, false );
    }

    public Date getDate( String parameterKey, boolean isNullable ) throws Exception
    {
    	return getDate( parameterKey, null, isNullable );
    }
    
    public Date getDate( String parameterKey ) throws Exception
    {
        return getDate( parameterKey, null );
    }
        
    public Date getDate( String parameterKey, String format ) throws Exception
    {
        return getDate( parameterKey, format, false );
    }

    public int getInt( String parameterKey, boolean isNullable ) throws Exception
    {
        Integer result = getParameter( parameterKey, isNullable, Integer.class );

        return ( null == result ) ? 0 : result.intValue();
    }

    public float getFloat( String parameterKey, boolean isNullable ) throws Exception
    {
        Float result = getParameter( parameterKey, isNullable, Float.class );

        return ( null == result ) ? 0 : result.floatValue();
    }

    public String getString( String parameterKey, boolean isNullable ) throws Exception
    {
        return getParameter( parameterKey, isNullable, String.class );
    }

    public boolean getBoolean( String parameterKey, boolean isNullable ) throws Exception
    {
        Boolean result = getParameter( parameterKey, isNullable, Boolean.class );

        return ( null == result ) ? false : result.booleanValue();
    }

    public Date getDate( String parameterKey, String format, boolean isNullable ) throws Exception
    {
        return getParameter( parameterKey, isNullable, Date.class, format );
    }
    
    private <T> T getParameter( String parameterKey, boolean isNullable, Class<T> clazz ) throws Exception
    {
        return getParameter( parameterKey, isNullable, clazz, null );
    }

    @SuppressWarnings( "unchecked" )
    private <T> T getParameter( String parameterKey, boolean isNullable, Class<T> clazz, String additionalParameter ) throws Exception
    {
        try
        {
            if ( null == parameterMap.get( parameterKey ) )
                if ( !isNullable )
                    throw new Exception( " The parameter " + parameterKey + " was not received" );
                else
                    return null;

            if ( clazz.equals( Integer.class ) )
            {
                return (T) ( new Integer( Integer.parseInt( parameterMap.get( parameterKey )[0] ) ) );
            }
            else if ( clazz.equals( Float.class ) )
            {
                return (T) ( new Float( Float.parseFloat( parameterMap.get( parameterKey )[0] ) ) );
            }
            else if ( clazz.equals( String.class ) )
            {
                return (T) parameterMap.get( parameterKey )[0];
            }
            else if ( clazz.equals( Boolean.class ) )
            {
                return (T) new Boolean( Boolean.parseBoolean( parameterMap.get( parameterKey )[0] ) );
            }
            else if ( clazz.equals( Date.class ) )
            {
                return (T) new SimpleDateFormat( null == additionalParameter ? ConstantsShared.defaultDateFormat : additionalParameter, Locale.ENGLISH ).parse( parameterMap.get( parameterKey )[0] );
            }
            else
                throw new Exception( "Attempt to deserialize unknown type " );
        }
        catch ( NumberFormatException exception )
        {
            logger.log( Level.WARNING, "Can't parse int or float " + exception.getLocalizedMessage() );

            throw new Exception( "The parameter " + parameterKey + " Can't be parsed to value" );
        }
        catch ( ParseException exception )
        {
            logger.log( Level.WARNING, "Can't parse date " + exception.getLocalizedMessage() );

            throw new Exception( "Can't parse date " + exception.getLocalizedMessage() );
        }
    }

    public Map<String, String[]> getParameterMap()
    {
        return parameterMap;
    }
}
