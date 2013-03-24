package com.rosinka.tt.server.base.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rosinka.tt.shared.UUID;

public class PerformanceLogger
{
	private static class OperationUnit
	{	
		private String id;
		
		public String getId()
		{
			return id;
		}

		@SuppressWarnings("unused")
		public void setId(String id)
		{
			this.id = id;
		}

		private String operationName;
			
		private long timeStart;
	
		private long timeFinish;
		
		private Logger logger;
		
		public OperationUnit(Logger logger, String operationName)
		{
			this.id = UUID.uuid();
			
			this.setLogger(logger);
			
			this.operationName = operationName;
			
			this.timeFinish = -1;
		}

		public String getOperationName()
		{
			return operationName;
		}

		@SuppressWarnings("unused")
		public void setOperationName(String operationName)
		{
			this.operationName = operationName;
		}

		public long getTimeStart()
		{
			return timeStart;
		}

		public void setTimeStart(long timeStart)
		{
			this.timeStart = timeStart;
		}

		public long getTimeFinish()
		{
			return timeFinish;
		}

		public void setTimeFinish(long timeFinish)
		{
			this.timeFinish = timeFinish;
		}

		public Logger getLogger()
		{
			return logger;
		}

		public void setLogger(Logger logger)
		{
			this.logger = logger;
		}	
	}
	
	static private final Logger logger = Logger.getLogger(PerformanceLogger.class.getSimpleName());
	
	static private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	static private final Map<String,OperationUnit> operations = new HashMap<String,OperationUnit>();
	
	public static String start(String operationName,Logger logger)
	{
		OperationUnit unit = new OperationUnit(logger, operationName);
		
		setTimeStamp(unit,true);
		
		operations.put(unit.getId(), unit);
		
		loggUnit(unit);
		
		return unit.getId();
	}
	
	public static void finish(String uuid)
	{
		if(operations.containsKey(uuid))
		{
			OperationUnit unit  = operations.get(uuid);
			
			setTimeStamp(unit,false);
			
			loggUnit(unit);
			
			operations.remove(unit);
		}
		else
			logger.log(Level.WARNING,"Unknow operation unit UUID");
	}
	
	private static OperationUnit setTimeStamp(OperationUnit unit, boolean ifStart)
	{
		Calendar cal = Calendar.getInstance();
		
		long currentTime = cal.getTimeInMillis();
		
		if(ifStart)
			unit.setTimeStart(currentTime);
		else
			unit.setTimeFinish(currentTime);
		
		return unit;
	}
	
	private static void loggUnit(OperationUnit unit)
	{
		StringBuilder logMessage = new StringBuilder();
		
		logMessage.append(unit.getOperationName());
		
		if(unit.getTimeFinish() == -1)
		{
			logMessage.append(" Start. ");
		}
		else
		{
		
			logMessage.append(" Finish. ");
			
			logMessage.append("Duration :=  " + (unit.getTimeFinish() - unit.getTimeStart()) + " ms");
		}
		
		logMessage.append("  .Logg at time : " + sdf.format(new Date()));
		
		if(null != unit.getLogger())
			unit.getLogger().log(Level.INFO, logMessage.toString());
	}
}
