package com.timetabling.shared.enums;

public enum TeacherRank {
	PROFESSOR (0), 
	DOZENT (1), 
	LECTURER (2), 
	ASSISTANT (3);
	int code;
	private TeacherRank(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	static public TeacherRank getByCode(int code) {
		return values()[code];
	}
}