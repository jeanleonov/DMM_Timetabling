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
	String getSpecialtyName();
	void setSpecialtyName(String specialtyName);
	long getSubjectId();
	void setSubjectId(long subjectId);
	String getSubjectName();
	void setSubjectName(String subjectName);
	int getLessonTypeCode();
	void setLessonTypeCode(int lessonTypeCode);
	Long getCathedraId();
	void setCathedraId(Long cathedraId);
	String getCathedraName();
	void setCathedraName(String cathedraName);
	byte getCourse();
	void setCourse(byte course);
	Long getJoinId();
	void setJoinId(Long joinId);
	Byte getNumberOfSubgroups();
	void setNumberOfSubgroups(Byte numberOfSubgroups);
	Byte getHoursInTwoWeeks();
	void setHoursInTwoWeeks(Byte hoursInTwoWeeks);
	String getDisplayName();
	void setDisplayName(String displayName);
	
}
