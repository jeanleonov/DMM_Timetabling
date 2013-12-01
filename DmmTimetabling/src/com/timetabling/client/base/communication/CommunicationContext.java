package com.timetabling.client.base.communication;

import com.timetabling.shared.ConstantsShared;

public class CommunicationContext {

	private Long cathedraID = null;
	private Long teacherID = null;
	private Integer year = 2013;
	private Boolean season = ConstantsShared.AUTUMN_WINTER;
	
	
	public Long getCathedraID() {
		return cathedraID;
	}
	public void setCathedraID(Long cathedraID) {
		this.cathedraID = cathedraID;
	}
	public Long getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(Long teacherID) {
		this.teacherID = teacherID;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Boolean getSeason() {
		return season;
	}
	public void setSeason(Boolean season) {
		this.season = season;
	}
	
}
