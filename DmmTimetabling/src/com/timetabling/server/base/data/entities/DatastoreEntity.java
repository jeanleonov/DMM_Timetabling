package com.timetabling.server.base.data.entities;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;
import com.timetabling.server.base.common.KeyHelper;

@Entity
public class DatastoreEntity
{
	@Unindexed
	private Integer version = 0;

	@Transient
	protected String entityKey;

    @PrePersist
    public void onPersist()
    {
        this.version++;
    }

	@PostLoad
	public void onLoad() throws Exception
	{
		this.entityKey = KeyHelper.getEntityKey(this);
	}
	
    public Integer getEntityVersion()
    {
        return version;
    }

	public void setEntityKey(String enityKey)
	{
		this.entityKey = enityKey;
	}

	public String getEntityKey()
	{
		return entityKey;
	}
}
