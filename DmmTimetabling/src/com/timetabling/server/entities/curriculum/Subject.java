package com.rosinka.tt.server.entities.curriculum;

import com.rosinka.tt.server.base.data.entities.DatastoreLongEntity;

public class Subject extends DatastoreLongEntity {

	private String name;

	public Subject() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
