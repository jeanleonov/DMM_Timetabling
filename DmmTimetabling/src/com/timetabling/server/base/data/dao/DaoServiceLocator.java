package com.timetabling.server.base.data.dao;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class DaoServiceLocator implements ServiceLocator {
	public Object getInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
