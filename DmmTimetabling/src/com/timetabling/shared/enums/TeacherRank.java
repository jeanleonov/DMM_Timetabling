package com.timetabling.shared.enums;

public enum TeacherRank {
	
	PROFESSOR (0, "Профессор"), 
	DOZENT (1, "Доцент"), 
	LECTURER (2, "Лектор"), 
	ASSISTANT (3, "Асистент");
	
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