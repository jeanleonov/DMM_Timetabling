package com.rosinka.tt.server.base.common;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.rosinka.tt.server.base.data.entities.DatastoreEntity;
import com.rosinka.tt.server.base.exceptions.InternalException;

public class KeyHelper
{
	private static final Logger logger = Logger.getLogger(KeyHelper.class.getSimpleName());
	
	public static <T extends DatastoreEntity> String getEntityKey(T entity)
	{
		ObjectifyFactory objectifyFactory = ObjectifyService.factory();
		
		return objectifyFactory.keyToString(objectifyFactory.getKey(entity));
	}
	
    /**
     * Convenience method to generate a typed Key<T> for a given id
     */
    public static <T> Key<T> getKey( Class<T> clazz, Long id )
    {
        return getKey( clazz, null, id );
    }

    /**
     * Convenience method to generate a typed Key<T> for a given id
     */
    public static <T> Key<T> getKey( Class<T> clazz, String id )
    {
        return getKey( clazz, null, id );
    }

    /**
     * Convenience method to generate a typed Key<T> for a given id and parent entity class
     */
    public static <T> Key<T> getKey( Class<T> clazz, Key<?> parentKey, Long id ) throws IllegalArgumentException
    {
        if ( null == id )
            throw new IllegalArgumentException();

        if ( null == parentKey )
        {
            return new Key<T>( clazz, id );
        }
        else
        {
            return new Key<T>( KeyFactory.createKey( parentKey.getRaw(), clazz.getSimpleName(), id ) );
        }
    }

    /**
     * Convenience method to generate a typed Key<T> for a given GAE entity key
     */
    public static <T> Key<T> getKey( Class<T> clazz, Key<?> parentKey, String id ) throws IllegalArgumentException
    {
        if ( null == id )
            throw new IllegalArgumentException();

        if ( null == parentKey )
        {
            return new Key<T>( clazz, id );
        }
        else
        {
            return new Key<T>( KeyFactory.createKey( parentKey.getRaw(), clazz.getSimpleName(), id ) );
        }
    }
    
    public static <T> Key<T> unmarshalKey(Class<T> clazz, String entityKey) throws InternalException
    {
    	 Key<T> key = null;

         try
         {
        	 key = new Key<T>( KeyFactory.stringToKey( entityKey ) );
         }
         catch ( IllegalArgumentException exception )
         {
             logger.log( Level.WARNING, exception.getLocalizedMessage() );

             throw new InternalException("Can't parse datastore key" );
         }
         
         return key;
    }

    public static <T> List<Key<T>> getKeys(Class<T> clazz, List<String> stringKeys) throws IllegalArgumentException
	{
		if (null == stringKeys)
		{
			throw new IllegalArgumentException();
		}

		List<Key<T>> keys = new ArrayList<Key<T>>();
		
		for(String stringKey : stringKeys)
		{
			keys.add(new Key<T>(KeyFactory.stringToKey(stringKey)));
		}
		
		return keys;
	}
}
