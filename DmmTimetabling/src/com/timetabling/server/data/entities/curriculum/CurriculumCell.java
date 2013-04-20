package com.timetabling.server.data.entities.curriculum;

import java.util.List;

import javax.persistence.Transient;

import com.googlecode.objectify.annotation.Unindexed;
import com.timetabling.server.base.data.entities.DatastoreLongEntity;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.managers.DaoFactory;
import com.timetabling.server.data.managers.curriculum.CurriculumExtensionSaver;
import com.timetabling.shared.enums.LessonType;

/** Curriculum is associated with specified specialty.
 *  It says how many hours of lectures and practices of subject should be listened in each semester.<br> <code>
 *  | ------- | -1-semester- | -2-semester- |<br>
 *  | Subject | lect | pract | lect | pract | ...<br>
 *  |---------|------|-------|------|-------|---<br>
 *  |_prog.___|_2____|_4_____|_0____|_0_____| ...<br>
 *  |_ukr.____|_1____|_0_____|_2____|_0_____| ...<br>
 *                      . . .<br> </code>
 *  class CurriculumCell is associated with concrete cell in curriculum.
 *  So it contains information from curriculum (specialty, semester, subject and hours in two week)
 *  and additionaly contains info about what cathedra assigned to this 'cell',
 *  how many subgroups 'has' this 'cell'
 *  */
public class CurriculumCell extends DatastoreLongEntity {
	
	private long specialtyId;
	private long subjectId;
	private int lessonTypeCode;
	private long cathedraId;
	
	/** 1-5 (year of studying) */
	private byte course;

	/** Can be null if cell don't belong to any join.
	 *  See 'CurriculumCellJoiner'.*/
	private Long joinId = null;
	
	/** For lectures it is usually 1, 
	 *  for practice it is usually number of groups (..MF-31, MF-32 - 2 groups) */
	@Unindexed private byte numberOfSubgroups;
		/** 2 if you have 1 full lesson,
	 *  1 if you have flashing lesson,
	 *  4 if you have 2 full lessons, ...*/
	@Unindexed private byte hoursInTwoWeeks;
	
	@Unindexed private String displayName;
	
	/** List of lessons which are originated by this 'Cell'.
	 * ( lessons.size() == numberOfSubgroups * (hoursInTwoWeeks+1)/2 ) */
	@Transient private List<Lesson> lessons;

	public CurriculumCell() {
	}
	
	/** This constructor is for curriculums reading process. <br>
	 * 	But it is not best decision! <br>
	 *  Because for each call of this constructor DB will be queried twice 
	 *  (for getting specialtyID by specialtyName and for getting subjectID by subjectName) */
	public CurriculumCell(String specialtyName, String subjectName, LessonType lessonType, byte hoursInTwoWeeks, byte course) throws Exception {
		this.specialtyId = DaoFactory.getSpecialtyManager().getSpecialtyIdFor(specialtyName);
		this.subjectId = DaoFactory.getSubjectManager().getSubjectIdFor(subjectName);
		this.lessonTypeCode = lessonType.getCode();
		this.hoursInTwoWeeks = hoursInTwoWeeks;
		this.course = course;
	}

	public long getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(long specialtyId) {
		this.specialtyId = specialtyId;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	/** @see com.timetabling.shared.enums.LessonType Type */
	public int getLessonTypeCode() {
		return lessonTypeCode;
	}

	/** @see com.timetabling.shared.enums.LessonType Type */
	public void setLessonTypeCode(int lessonTypeCode) {
		this.lessonTypeCode = lessonTypeCode;
	}

	public long getCathedraId() {
		return cathedraId;
	}

	public void setCathedraId(long cathedraId) {
		this.cathedraId = cathedraId;
	}

	/** Is for using reflect.
	 * 	@See {@link CurriculumExtensionSaver} */
	public void setCathedraId(Long cathedraId) {
		this.cathedraId = cathedraId;
	}

	public byte getCourse() {
		return course;
	}

	public void setCourse(byte course) {
		this.course = course;
	}

	public Long getJoinId() {
		return joinId;
	}

	/** Is for using reflect.
	 * 	@See {@link CurriculumExtensionSaver} */
	public void setJoinId(Long joinId) {
		this.joinId = joinId;
	}

	public byte getNumberOfSubgroups() {
		return numberOfSubgroups;
	}

	public void setNumberOfSubgroups(byte numberOfSubgroups) {
		this.numberOfSubgroups = numberOfSubgroups;
	}

	/** Is for using reflect.
	 * 	@See {@link CurriculumExtensionSaver} */
	public void setNumberOfSubgroups(Byte numberOfSubgroups) {
		this.numberOfSubgroups = numberOfSubgroups;
	}

	public byte getHoursInTwoWeeks() {
		return hoursInTwoWeeks;
	}

	public void setHoursInTwoWeeks(byte hoursInTwoWeeks) {
		this.hoursInTwoWeeks = hoursInTwoWeeks;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	
}
