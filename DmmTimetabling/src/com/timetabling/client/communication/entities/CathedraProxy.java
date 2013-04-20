package com.timetabling.client.communication.entities;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.timetabling.server.base.data.ObjectifyLongLocator;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;

@ProxyFor( value = Cathedra.class, locator = ObjectifyLongLocator.class )
public interface CathedraProxy extends EntityProxy {

	Long getId();
	void setId(Long id);
	String getName();
	void setName(String name);
	String getEmail();
	void setEmail(String email);
}
