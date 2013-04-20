package com.timetabling.shared.enums;

public enum WishPriority{
	WELL (0), 
	BAD (1), 
	IMPOSSIBLE (2);
	
	int code;
	private WishPriority(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	static public WishPriority getByCode(int code) {
		return values()[code];
	}
}
