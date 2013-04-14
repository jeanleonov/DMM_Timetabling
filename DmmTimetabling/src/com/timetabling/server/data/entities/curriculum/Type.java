package com.timetabling.server.data.entities.curriculum;



public enum Type {
	
	LECTION (0),
	PRACTICE (1),
	PRACTICE_IN_COMPUTER_ROOM (2),
	SPECIAL_COURSE (3),
	SPECIAL_COURSE_IN_COMPUTER_ROOM (4);
	
	int code;
	
	private Type(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	static public Type getByCode(int code) {
		return values()[code];
	}
	
}
