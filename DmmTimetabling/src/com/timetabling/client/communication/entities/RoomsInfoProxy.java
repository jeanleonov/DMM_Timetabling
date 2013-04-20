package com.timetabling.client.communication.entities;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.timetabling.server.base.data.ObjectifyLongLocator;
import com.timetabling.server.data.entities.curriculum.extentions.RoomsInfo;

@ProxyFor( value = RoomsInfo.class, locator = ObjectifyLongLocator.class )
public interface RoomsInfoProxy extends EntityProxy {

	int getNumberOfComuterRooms();
	void setNumberOfComuterRooms(int numberOfComuterRooms);
	int getNumberOfRooms();
	void setNumberOfRooms(int numberOfRooms);
	
}
