package com.timetabling.client.communication.entities;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.timetabling.server.base.data.ObjectifyLongLocator;
import com.timetabling.server.data.entities.timetabling.Time;

@ProxyFor( value = Time.class, locator = ObjectifyLongLocator.class )
public interface TimeProxy extends EntityProxy {

	int getWeekDay();
	void setWeekDay(int weekDay);
	int getLessonNumber();
	void setLessonNumber(int lessonNumber);
	int getWeekTypeCode();
	void setWeekTypeCode(int weekTypeCode);
	
}
