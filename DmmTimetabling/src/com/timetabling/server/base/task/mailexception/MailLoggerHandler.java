package com.rosinka.tt.server.base.task.mailexception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


public class MailLoggerHandler extends Handler
{
	public MailLoggerHandler()
	{
		super();
	}

	@Override
	public void publish(LogRecord record)
	{
		if (record.getLevel().equals(Level.SEVERE))
		{
			String payload = null;

			if (null != record.getThrown())
			{
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);

				record.getThrown().printStackTrace(pw);

				sw.flush();
				pw.flush();
				pw.close();

				payload = sw.toString(); // stack trace as a string

			}
			else if (null != record.getMessage())
			{
				payload = record.getMessage();
			}

			if (null != payload)
				MailExceptionTaskExecutor.executeTask(payload);
		}
	}

	@Override
	public void flush()
	{
		// Left blank because output stream used is already closed

	}

	@Override
	public void close() throws SecurityException
	{
		// Left blank because output stream used is already closed
	}
}
