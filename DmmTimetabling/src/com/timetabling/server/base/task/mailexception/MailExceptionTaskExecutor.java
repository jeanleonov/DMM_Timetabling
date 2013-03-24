package com.rosinka.tt.server.base.task.mailexception;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.apphosting.api.ApiProxy.OverQuotaException;
import com.rosinka.tt.shared.ConstantsServer;
import com.rosinka.tt.shared.UUID;

public class MailExceptionTaskExecutor
{
	private static final Logger logger = Logger.getLogger(MailExceptionTaskExecutor.class.getSimpleName());

	public static void executeTask(String payload)
	{
		if (!UserServiceFactory.getUserService().isUserLoggedIn())
		{
			return;
		}

		// get logging queue
		Queue queue = QueueFactory.getQueue(ConstantsServer.LOGGINGQUEUE);

		try
		{
			// create task with corresponding parameters
			TaskOptions taskOptions = TaskOptions.Builder.withUrl(ConstantsServer.MAILEXCEPTIONTASKURL).param(ConstantsServer.EXCEPTIONBODY, payload).param(ConstantsServer.LOGGEDINUSEREMAIL, UserServiceFactory.getUserService().getCurrentUser().getEmail());

			// set task name
			taskOptions.taskName(UUID.uuid());

			// enqueue current task
			queue.add(taskOptions);
		}
		catch (OverQuotaException exception)
		{
			logger.warning("Overquota exception while executing mail exception task");
		}
		catch (IllegalArgumentException exception)
		{
			logger.log(Level.WARNING, "Can't fetch specified queue", exception);
		}
	}
}
