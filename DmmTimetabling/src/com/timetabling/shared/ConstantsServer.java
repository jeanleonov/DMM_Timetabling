package com.timetabling.shared;

public class ConstantsServer{
	
	static public final String SAVE_CURRICULUM = "Save unextended curriculum";
	
	//email exception task
	static public final String EXCEPTIONBODY = "exceptionBody";
	
	static public final String LOGGEDINUSEREMAIL = "loggedinuseremail";
	
	static public final String ADMIN_EMAIL = "marchev.vladimir@gmail.com";

	//task execution max time in mili (set 1 minute less in compare to quota )
	static public final int MAXTASKEXECUTIONTIME = 9*60*1000; 
	
	static public final int MAX_FETCH_SIZE = 1000;
	
	//task parameters
	static public final String ENTITYKEY = "entityKey";
	
	static public final String CURSORPROPERTY = "cursor";
	
	//sendmail task
	static public final String RECIPIENTS = "recipients";
	
	static public final String MAILBODY = "mailbody";
	
	static public final String MAILSUBJECT = "subject";
	
	//Application QUEUES
	static public final String LOGGINGQUEUE = "logging";
	
	static public final String DELETIONQUEUE = "deletion";
	
	// quety to hold stats collector tasks
	static public final String STATSQUEUE = "stats";
	
	//tasks URLs
	static public final String MAILEXCEPTIONTASKURL = "/tasks/mailexception";
	
	static public final String RECALCULATIONINGREDIENTTASKURL = "/tasks/recalculateingredient";
	
	static public final String GLUSERDELETIONTASKURL = "/tasks/gluserdeletion";
	
	static public final String SENDMAILTASKURL = "/tasks/sendmail";
		
	static public final String STATSPERSISTTASKURL = "/tasks/statspersist";
	
	static public final String MONTHSSTATSPERSISTTASKURL = "/tasks/monthsstatspersist";
	
	//GAE task headers
	static public final String APPINITIATEDCRONHEADER = "X-AppEngine-Cron";
	
	static public final String APPENGINETASHNAMEHEADER = "X-AppEngine-TaskName";
	
	// daily task QUEUE API calls(enqueued tasks) quota
	static public final int MAX_QUEUE_SIZE = 100000;
	
	static public final String blobKeyForImage = "image";

}
