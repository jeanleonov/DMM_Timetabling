package com.timetabling.shared.enums;


public enum RuleType {

	TYPE1 (0),
	TYPE2 (1);
	
	int code;
	private RuleType(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	static public RuleType getByCode(int code) {
		return values()[code];
	}
	
}
