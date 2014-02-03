package com.timetabling.client.base.communication;

import com.timetabling.shared.ConstantsShared;

public class CommunicationContext {

	private Long cathedraID = null;
	private String cathedraName = null;
	private Long teacherID = null;
	private String teacherName = null;
	private Integer year = 2013;
	private Boolean season = ConstantsShared.AUTUMN_WINTER;
	
	public Long getCathedraID() {
		return cathedraID;
	}
	public void setCathedraID(Long cathedraID) {
		this.cathedraID = cathedraID;
	}
	public String getCathedraName() {
		return cathedraName;
	}
	public void setCathedraName(String cathedraName) {
		this.cathedraName = cathedraName;
	}
	public Long getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(Long teacherID) {
		this.teacherID = teacherID;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
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
