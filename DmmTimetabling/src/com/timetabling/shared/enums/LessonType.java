package com.timetabling.shared.enums;

public enum LessonType {
	
	LECTION (0, "Лекция"),
	PRACTICE (1, "Практика"),
	PRACTICE_IN_COMPUTER_ROOM (2, "Практика в комп.ауд."),
	SPECIAL_COURSE (3, "Спецкурс"),
	SPECIAL_COURSE_IN_COMPUTER_ROOM (4, "Спецкурс в комп.ауд.");
	
	private int code;
	private String displayName;
	
	private LessonType(int code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	static public LessonType getByCode(int code) {
		return values()[code];
	}
}
