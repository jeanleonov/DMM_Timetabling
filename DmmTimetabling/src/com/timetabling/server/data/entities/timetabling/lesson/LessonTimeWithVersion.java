package com.timetabling.server.data.entities.timetabling.lesson;

import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;
import com.timetabling.server.data.entities.timetabling.Time;

/** Every 1 Lesson is associated with N LessonTimeWithVersion, 
 *  which is associated with Version.
 *  It means that Lesson can has different times in different versions of timetable */
public class LessonTimeWithVersion extends DatastoreLongEntity {
	
	@Parent private Key<Lesson> parent;
	private int timeCode = -1;
	private long versionId;
	
	@Transient private Time time = null;
	
	public LessonTimeWithVersion() {
	}

	public Key<Lesson> getParent() {
		return parent;
	}

	public void setParent(Key<Lesson> parent) {
		this.parent = parent;
	}

	public Time getTime() {
		if (time == null) {
			if (timeCode == -1)
				return null;
			time = new Time(timeCode);
		}
		return time;
	}

	public void setTime(Time time) {
		this.timeCode = time.getKey();
		this.time = time;
	}

	public long getVersionKey() {
		return versionId;
	}

	public void setVersionKey(long versionId) {
		this.versionId = versionId;
	}	

}
