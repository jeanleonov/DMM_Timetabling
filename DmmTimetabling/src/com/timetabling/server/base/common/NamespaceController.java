package com.timetabling.server.base.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.NamespaceManager;

/**
 * Contains required logic to perform user verification
 */
public class NamespaceController {
	
	public static final String generalNamespace = "general";
	
	private static class AuthorizationControllerHolder
	{
		private final static NamespaceController INSTANCE = new NamespaceController();
	}

	private final Logger logger = Logger.getLogger(NamespaceController.class.getSimpleName());

	private NamespaceController()
	{

	}

	/**
	 * @return singleton instance of AuthorizationController.class
	 */
	public static NamespaceController getInstance()
	{
		return AuthorizationControllerHolder.INSTANCE;
	}

	/**
	 * Set, if required, application namespace. In case of illegal namespace
	 * name throws runtime exception, caught by dispatch method
	 * 
	 * @param namespaceName
	 */
	public void updateNamespace(String namespaceName)
	{
		if (null != NamespaceManager.get())
		{
			if (NamespaceManager.get().equals(namespaceName))
			{
				return;
			}
		}

		if (null != namespaceName)
		{
			NamespaceManager.set(namespaceName);

			logger.log(Level.INFO, namespaceName);
		}
	}
}
