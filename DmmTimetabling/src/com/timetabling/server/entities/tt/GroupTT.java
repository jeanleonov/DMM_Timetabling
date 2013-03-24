package com.rosinka.tt.server.entities.tt;

import java.util.List;

import com.rosinka.tt.server.generating.Markable;

public class GroupTT implements Markable{

	private String groupName;
	private byte groupNumber;
	private List<Lesson> lessons;
	
	public GroupTT() {
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public byte getGroupNumber() {
		return groupNumber;
	}
	
	public void setGroupNumber(byte groupNumber) {
		this.groupNumber = groupNumber;
	}
	
	public List<Lesson> getLessons() {
		return lessons;
	}
	
	public double getMarkForVersion(Version version) {
		// TODO
		return 1d;
	}
	
}
