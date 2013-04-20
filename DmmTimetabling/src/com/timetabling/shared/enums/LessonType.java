package com.timetabling.shared.enums;

public enum LessonType {
	
	LECTION (0),
	PRACTICE (1),
	PRACTICE_IN_COMPUTER_ROOM (2),
	SPECIAL_COURSE (3),
	SPECIAL_COURSE_IN_COMPUTER_ROOM (4);
	
	int code;
	
	private LessonType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	static public LessonType getByCode(int code) {
		return values()[code];
	}
}
