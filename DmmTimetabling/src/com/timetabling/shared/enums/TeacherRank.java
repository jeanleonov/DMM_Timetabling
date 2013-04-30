package com.timetabling.shared.enums;

public enum TeacherRank {
	
	PROFESSOR (0, "Professor"), 
	DOZENT (1, "Dozent"), 
	LECTURER (2, "Lecturer"), 
	ASSISTANT (3, "Assistant");
	
	private int code;
	private String displayName;
	
	private TeacherRank(int code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	static public TeacherRank getByCode(int code) {
		return values()[code];
	}
}