package com.rosinka.tt.server.entities.curriculum.extentions;

import java.util.List;

import javax.persistence.Transient;

import com.googlecode.objectify.annotation.Unindexed;
import com.rosinka.tt.server.base.data.entities.DatastoreLongEntity;

public class Cathedra extends DatastoreLongEntity {
	
	private String name;
	@Unindexed private String email;
	private long account;
	
	@Transient List<Teacher> teachers;

	public Cathedra() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getAccount() {
		return account;
	}

	public void setAccount(long account) {
		this.account = account;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

}
