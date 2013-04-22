package com.timetabling.server.data.entities.curriculum;

import com.timetabling.server.base.data.entities.DatastoreLongEntity;

public class Subject extends DatastoreLongEntity {

	private String name;
	private String displayName;

	public Subject() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
