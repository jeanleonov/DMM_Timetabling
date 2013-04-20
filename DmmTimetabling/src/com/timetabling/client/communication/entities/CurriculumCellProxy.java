package com.timetabling.client.communication.entities;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.timetabling.server.base.data.ObjectifyLongLocator;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;

@ProxyFor( value = CurriculumCell.class, locator = ObjectifyLongLocator.class )
public interface CurriculumCellProxy extends EntityProxy {

	Long getId();
	void setId(Long id);
	long getSpecialtyId();
	void setSpecialtyId(long specialtyId);
	long getSubjectId();
	void setSubjectId(long subjectId);
	int getLessonTypeCode();
	void setLessonTypeCode(int lessonTypeCode);
	long getCathedraId();
	void setCathedraId(long cathedraId);
	void setCathedraId(Long cathedraId);
	byte getCourse();
	void setCourse(byte course);
	Long getJoinId();
	void setJoinId(Long joinId);
	byte getNumberOfSubgroups();
	void setNumberOfSubgroups(byte numberOfSubgroups);
	void setNumberOfSubgroups(Byte numberOfSubgroups);
	byte getHoursInTwoWeeks();
	void setHoursInTwoWeeks(byte hoursInTwoWeeks);
	String getDisplayName();
	void setDisplayName(String displayName);
	
}
