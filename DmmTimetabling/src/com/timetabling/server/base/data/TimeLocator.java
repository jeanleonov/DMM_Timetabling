package com.timetabling.server.base.data;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.timetabling.server.data.entities.timetabling.Time;

public class TimeLocator extends Locator<Time, Integer> {
	@Override
	public Time create(Class<? extends Time> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Time find(Class<? extends Time> clazz, Integer id) {
		return new Time(id);
	}

	@Override
	public Class<Time> getDomainType() {
		// Never called
		return null;
	}

	@Override
	public Integer getId(Time domainObject) {
		return domainObject.getKey();
	}

	@Override
	public Class<Integer> getIdType() {
		return Integer.class;
	}

	@Override
	public Object getVersion(Time domainObject) {
		return 0;
	}
}
