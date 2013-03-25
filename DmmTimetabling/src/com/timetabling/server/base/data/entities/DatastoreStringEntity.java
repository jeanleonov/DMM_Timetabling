package com.timetabling.server.base.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DatastoreStringEntity extends DatastoreEntity
{
	@Id
	protected String id;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
