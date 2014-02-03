package com.timetabling.client.communication.entities;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.timetabling.server.base.data.ObjectifyLongLocator;
import com.timetabling.server.data.entities.curriculum.extentions.Wish;
import com.timetabling.shared.enums.TeacherRank;
import com.timetabling.shared.enums.WishPriority;

@ProxyFor( value = Wish.class, locator = ObjectifyLongLocator.class )
public interface WishProxy extends EntityProxy {

	Long getId();
	void setId(Long id);
	int getTimeKey();
	void setTimeKey(int timeKey);
	int getPriorityCode();
	void setPriorityCode(int priorityCode);
	WishPriority getPriority();
	void setPriority(WishPriority priority);
	TeacherRank getTeacherRank();
	void setTeacherRank(TeacherRank teacherRank);
	
}
