package com.timetabling.client.communication.entities;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.timetabling.server.base.data.ObjectifyLongLocator;
import com.timetabling.server.data.entities.curriculum.extentions.Teacher;

@ProxyFor( value = Teacher.class, locator = ObjectifyLongLocator.class )
public interface TeacherProxy extends EntityProxy {

	Long getId();
	void setId(Long id);
	String getName();
	void setName(String name);
	Integer getRankCode();
	void setRankCode(Integer rankCode);
}
