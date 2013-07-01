package com.timetabling.server.data.entities.curriculum;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;

@Cached
public class Specialty extends DatastoreLongEntity implements Serializable {

	private static final long serialVersionUID = -704732242181384961L;
	
	private String name;
	/** Short of full specialty name. For example: ��, �� ..*/
	@Unindexed private String shortName;
	
	public Specialty() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** Short of full specialty name. For example: ��, �� ..*/
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
