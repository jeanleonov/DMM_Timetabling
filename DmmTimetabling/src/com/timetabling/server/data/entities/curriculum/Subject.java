package com.timetabling.server.data.entities.curriculum;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Cached;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;

@Cached
public class Subject extends DatastoreLongEntity implements Serializable {
	
	private static final long serialVersionUID = 5803682877340257805L;
	
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
