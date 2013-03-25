package com.timetabling.server.base.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Entity
@Cached
public class DatastoreLongEntity extends DatastoreEntity
{
	@Id
	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
