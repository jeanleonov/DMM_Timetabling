package com.timetabling.server.data.entities.timetabling;


/** Describes specific lesson time.
 *  For example: 1st lesson in Monday on lower week */
public class Time {
	
	enum WeekType{
		
		/**Lower week (нижн€€ недел€)*/ LOWER (0),
		
		/**Upper week (верхн€€ недел€)*/UPPER (1),
		
		/**Not flashing week (обычна€ немигающа€ пара)*/ FULL (2),
		
		UNDEF (3), 
		
		/** ANY recommended to use for Wish.*/ ANY (4);
		
		int code;
		private WeekType(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
		static public WeekType getByCode(int code) {
			switch (code) {
			case 0: return LOWER;
			case 1: return UPPER;
			case 2: return FULL;
			case 3: return UNDEF;
			case 4: return ANY;
			default: return UNDEF;
			}
		}
	}
	
	/** ANY recommended to use for Wish  */
	public final static int ANY = -1;
	public final static int UNDEF = -2;
	
	/** Monday, Tuesday, ... */
	private int day = UNDEF;
	private int lessonNumber = UNDEF;
	private WeekType weekType = WeekType.UNDEF;
	
	public Time(int timeKey) {
		lessonNumber = timeKey / 10000;
		day = (timeKey - lessonNumber*10000) / 100;
		weekType = WeekType.getByCode(timeKey - lessonNumber*10000 - day*100);
	}

	public int getWeekDay() {
		return day;
	}

	public void setWeekDay(int weekDay) {
		this.day = weekDay;
	}

	public int getLessonNumber() {
		return lessonNumber;
	}

	public void setLessonNumber(int lessonNumber) {
		this.lessonNumber = lessonNumber;
	}

	public WeekType getWeekType() {
		return weekType;
	}

	public void setWeekType(WeekType weekType) {
		this.weekType = weekType;
	}
	
	public int getKey() {
		return weekType.getCode() + 100*day + 10000*lessonNumber;
	}
	
	/**
	 * hasConflictWith or doesIntersectWith
	 * @return true if times are intersecting. You should remember that ANY intersect with anything except UNDEF
	 * @throws Exception if some field of _this or _t is undefined
	 */
	public boolean hasConflictWith(Time t) throws Exception{
		if (  day==UNDEF ||   lessonNumber==UNDEF ||   weekType == WeekType.UNDEF ||
			t.day==UNDEF || t.lessonNumber==UNDEF || t.weekType == WeekType.UNDEF)
			throw new Exception("The times are not fully defined");
		if (day==ANY || t.day==ANY || day==t.day)
			if (lessonNumber==ANY || t.lessonNumber==ANY || lessonNumber==t.lessonNumber)
				if (weekType==WeekType.ANY || t.weekType==WeekType.ANY || weekType==t.weekType)
					return true;
		return false;
	}

}
