package com.rosinka.tt.server.base.data.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.apphosting.api.ApiProxy.OverQuotaException;
import com.google.apphosting.api.DeadlineExceededException;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.rosinka.tt.server.base.common.PerformanceLogger;
import com.rosinka.tt.server.base.exceptions.InternalException;
import com.rosinka.tt.server.base.exceptions.InternalNotForUserException;

/**
 * DAO that encapsulates a single transaction.
 */
public class DAOT
{
	static final boolean	IN_TRANSACTION=true,
							WITHOUT_TRANSACTION=false;
	
	protected final Logger logger;
	
	protected Objectify ofy;

    protected boolean isTransactionable;
    
    /** Alternate interface to Runnable for executing transactions */
    public static interface DatastoreOperation<T>
    {
        T run( DAOT daot ) throws Exception;
        
        String getOperationName();
    }
    
    /** Starts out with a transaction and session cache */
    protected DAOT(Logger logger, boolean isTransactionable)
    {
    	this.logger = logger;
    	
    	this.isTransactionable = isTransactionable;
    	
    	if(isTransactionable)
        {
    		startNewDataStoreTransaction();
        }
    	else
    	{
    		ofy = ObjectifyService.begin();
    	}
    }

    /**
     * Create a default DAOT and run the transaction through it
     * 
     * @throws InternalException
     */
    public static <T> T runInTransaction( Logger logger, DatastoreOperation<T> operation ) throws Exception
    {
    	if(null == logger)
    		throw new InternalException("Logger passed in DAOT is null");
    	
        DAOT daot = new DAOT(logger, IN_TRANSACTION);

        return daot.doOperation( operation );
    }
    
    /**
     * Create a default DAOT and run the transaction through it
     * 
     * @throws InternalException
     */
    public static <T> T runWithoutTransaction( Logger logger, DatastoreOperation<T> operation ) throws Exception
    {
    	if(null == logger)
    		throw new InternalException("Logger passed in DAOT is null");
    	
        DAOT daot = new DAOT(logger, WITHOUT_TRANSACTION);

        return daot.doOperation( operation );
    }    

    /**
     * Executes the task in the transactional context of this DAO/ofy.
     * 
     * @throws InternalException
     */
    public <T> T doOperation( DatastoreOperation<T> operation ) throws Exception
    {
    	String transactionUUID = PerformanceLogger.start(operation.getOperationName(), logger);
    	
        T result = null;

        try
        {
            result = operation.run( this );

            if(isTransactionable)
            {
                if ( ofy.getTxn().isActive() )
                {
                    ofy.getTxn().commit();
                }
            }
        }
        // current operation processing took more time than specified in quotas time limit
        catch ( DeadlineExceededException exception )
        {
            logger.log( Level.SEVERE, " Deadline exceeed exception.", exception );

            throw exception;
        }
        // in case daily application quota was exceeded
        catch ( OverQuotaException exception )
        {
            logger.log( Level.WARNING, "Task Queue Quotas exceeded! Can't add task into queue!", exception );
            
            throw exception;
        }
        catch ( NotFoundException exception )
        {
            logger.log( Level.WARNING, "Entity not found", exception );

            throw exception;
        }
        catch ( Exception exception )
        {
            if ( exception instanceof InternalException )
            {
                logger.log( Level.WARNING, "Internal Exception", exception );
                                
                throw exception;
            }
            if ( exception instanceof InternalNotForUserException )
            {
                logger.log( Level.SEVERE, "Internal Exception", exception );
            }
            else
            {
                logger.log( Level.SEVERE, "Datastore exception", exception );
                                
                throw exception;
            }
        }
        finally
        {
			if ( isTransactionable)
			{
				if ( ofy.getTxn().isActive())
				{
					ofy.getTxn().rollback();

				}
			}
            
            PerformanceLogger.finish(transactionUUID);
        }

        return result;
    }

    public void enqueueTransactionTask( String queueName, TaskOptions taskOptions ) throws Exception
    {
        try
        {
            // get recalculation queue
            Queue queue = QueueFactory.getQueue( queueName );
                        
            // enqueue current task
            if(isTransactionable)
            {
                queue.add( getOfy().getTxn(), taskOptions );            	
            }
            else
            {
            	queue.add(taskOptions);
            }
        }
        catch ( OverQuotaException exception )
        {
            throw new InternalException( "Overquota exception");
        }
        catch ( IllegalArgumentException exception )
        {
            logger.log(Level.SEVERE, "Can't fetch specified queue " + queueName, exception);
        }
        catch(Exception exception)
        {
        	logger.log(Level.SEVERE, "Can't execute task in queue " + queueName, exception);
        }
    }
    
	public Objectify getOfy()
    {
        return ofy;
    }

	public void startNewDataStoreTransaction()
    {
        ofy = ObjectifyService.beginTransaction();
    }
}
