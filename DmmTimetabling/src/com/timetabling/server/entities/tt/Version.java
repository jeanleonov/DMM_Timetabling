package com.rosinka.tt.server.entities.tt;

import java.util.Date;

import com.rosinka.tt.server.base.data.entities.DatastoreLongEntity;

/** Version of timetable.
 *  Every 1 Lesson is associated with N LessonTimeWithVersion, 
 *  which is associated with Version.
 *  It means that Lesson can has different times in different versions of timetable */
public class Version extends DatastoreLongEntity {
	
	private String description;
	private Date lastUpdate;
	private Double mark;
	
	public Version() {
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getMark() {
		return mark;
	}

	public void setMark(Double mark) {
		this.mark = mark;
	}

}
