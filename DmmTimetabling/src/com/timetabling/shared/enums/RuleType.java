package com.timetabling.shared.enums;


public enum RuleType {

	WITHOUT_COLLISIONS (0, -10),
	DAYS_LOADING (1, 1);
	
	int code;
	float priority;
	
	private RuleType(int code, float priority) {
		this.code = code;
		this.priority = priority;
	}
	
	public int getCode() {
		return code;
	}
	
	public float getPriority() {
		return priority;
	}

	static public RuleType getByCode(int code) {
		return values()[code];
	}
	
}
