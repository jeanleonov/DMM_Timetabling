package com.timetabling.server.base.task.mailexception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
import com.timetabling.server.base.common.RequestValidator;
import com.timetabling.shared.ConstantsServer;

public class MailExceptionTask extends HttpServlet
{
	private static final long serialVersionUID = -7910922856475275121L;

	private static final Logger logger = Logger.getLogger(MailExceptionTask.class.getName());

	static final String lineSeparator = System.getProperty("line.separator");

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		// check whether request is initiated by administrator and contains
		// required header
		if (null == request.getHeader(ConstantsServer.APPENGINETASHNAMEHEADER))
		{
			throw new ServletException("Unknown task initiator");
		}

		try
		{
			@SuppressWarnings("unchecked")
			RequestValidator requestValidator = new RequestValidator(request.getParameterMap());

			emailException(requestValidator);
		}
		catch (Exception exception)
		{
			throw new ServletException(exception);
		}
	}

	private void emailException(RequestValidator requestValidator) throws Exception
	{
		try
		{
			MailService service = MailServiceFactory.getMailService();

			MailService.Message msg = new MailService.Message();

			// service is accessible only by logged in user
			msg.setSender(ConstantsServer.ADMIN_EMAIL);

			msg.setSubject("Exception occured in namescpace - " + NamespaceManager.get());

			StringBuilder text = new StringBuilder();

			text.append("Email of request executor : " + requestValidator.getString(ConstantsServer.LOGGEDINUSEREMAIL));

			text.append(lineSeparator);

			text.append(requestValidator.getString(ConstantsServer.EXCEPTIONBODY));

			msg.setTextBody(text.toString());

			service.sendToAdmins(msg);
		}
		catch (Exception exception)
		{
			logger.log(Level.WARNING, "Exception when sending log message", exception);

			throw new Exception("Exception when sending log message", exception);
		}
	}
}
