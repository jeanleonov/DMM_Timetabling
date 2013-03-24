package com.rosinka.tt.server.entities.curriculum;

import java.util.List;

import javax.persistence.Transient;

import com.googlecode.objectify.annotation.Unindexed;
import com.rosinka.tt.server.base.data.entities.DatastoreLongEntity;
import com.rosinka.tt.server.entities.tt.Lesson;

/** Curriculum is associated with specified specialty.
 *  It says how many hours of lectures and practices of subject should be listened in each semester.<br>
 *     |         |  1 semester  | 2 semester   |<br>
 *  ~~~| Subject | lect | pract | lect | pract | ...<br>
 *     |---------|------|-------|------|-------|---<br>
 *     | prog.   | 2    | 4     | 0    | 0     | ...<br>
 *     | ukr.    | 1    | 0     | 2    | 0     | ...<br>
 *                      . . .<br>
 *  class CurriculumCell is associated with concrete cell in curriculum.
 *  So it contains information from curriculum (specialty, semester, subject and hours in two week)
 *  and additionaly contains info about what cathedra assigned to this 'cell',
 *  how many subgroups 'has' this 'cell'
 *  */
public class CurriculumCell extends DatastoreLongEntity {
	
	private long specialtyId;
	private long subjectId;
	private int lessonTypeId;
	private long cathedraId;
	
	/** Can be null if cell don't belong to any join.
	 *  See 'CurriculumCellJoiner'.*/
	private long joinId;
	
	/** For lectures it is usually 1, 
	 *  for practice it is usually number of groups (..MF-31, MF-32 - 2 groups) */
	@Unindexed private byte numberOfSubgroups;
	
	/** 2 if you have 1 full lesson,
	 *  1 if you have flashing lesson,
	 *  4 if you have 2 full lessons, ...*/
	@Unindexed private byte hoursInTwoWeeks;
	
	/** List of lessons which are originated by this 'Cell'.
	 * ( lessons.size() == numberOfSubgroups * (hoursInTwoWeeks+1)/2 ) */
	@Transient private List<Lesson> lessons;

	public CurriculumCell() {
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

	public int getLessonTypeId() {
		return lessonTypeId;
	}

	public void setLessonTypeId(int lessonTypeId) {
		this.lessonTypeId = lessonTypeId;
	}

	public long getCathedraId() {
		return cathedraId;
	}

	public void setCathedraId(long cathedraId) {
		this.cathedraId = cathedraId;
	}

	public long getJoinId() {
		return joinId;
	}

	public void setJoinId(long joinId) {
		this.joinId = joinId;
	}

	public byte getNumberOfSubgroups() {
		return numberOfSubgroups;
	}

	public void setNumberOfSubgroups(byte numberOfSubgroups) {
		this.numberOfSubgroups = numberOfSubgroups;
	}

	public byte getHoursInTwoWeeks() {
		return hoursInTwoWeeks;
	}

	public void setHoursInTwoWeeks(byte hoursInTwoWeeks) {
		this.hoursInTwoWeeks = hoursInTwoWeeks;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	
}
