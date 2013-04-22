package com.timetabling.server.data.entities.curriculum;

import com.googlecode.objectify.annotation.Unindexed;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;

public class Specialty extends DatastoreLongEntity {

	private String name;
	/** Short of full specialty name. For example: ÌÔ, ÌÏ ..*/
	@Unindexed private String shortName;
	
	public Specialty() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** Short of full specialty name. For example: ÌÔ, ÌÏ ..*/
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
