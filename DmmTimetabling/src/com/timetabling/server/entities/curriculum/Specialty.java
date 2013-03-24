package com.rosinka.tt.server.entities.curriculum;

import com.rosinka.tt.server.base.data.entities.DatastoreLongEntity;

public class Specialty extends DatastoreLongEntity {

	private String name;
	
	public Specialty() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
