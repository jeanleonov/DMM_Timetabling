package com.timetabling.server.data.managers;

import java.util.List;

import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.extentions.Teacher;
import com.timetabling.server.data.entities.tt.Lesson;

public class LessonsManager extends GenericDAO<Lesson> {

	/** Access modifier is DEFAULT here and it is not accident */
	LessonsManager() {
		super(Lesson.class);
	}

	/**
	 * @param curYear - '2012' <=> academic year 2012-2013
	 * @param curSemester -	'1' <=> 1_September_2012 - 1_January_2013,
	 * 						'2' <=> 1_January_2013   - 1_July_2013
	 * @return
	 * - all the lessons that this teacher has;
	 * - phantom lessons created from teacher's Wishes with priority IMPOSSIBLE (stub for identification busy time)
	 * */
	public List<Lesson> getLessonsForTeacher(Teacher teacher, short curYear, byte semester){
		// TODO
		return null;
	}
	
	/**
	 * @param specialtyName
	 * @param year - 1-4 for bachelors and 5 for masters
	 * @param semester - 1 or 2
	 * @param groupNumber
	 * */
	public List<Lesson> getLessonsForGroup(String specialtyName, byte year, byte semester, byte groupNumber){
		// TODO
		return null;
	}

	/**
	 * @param curYear - '2012' <=> academic year 2012-2013
	 * @param curSemester -	'1' <=> 1_September_2012 - 1_January_2013,
	 * 						'2' <=> 1_January_2013   - 1_July_2013
	 * */
	public Long getLastVersionNumber(short curYear, byte semester){
		// TODO
		return null;
	}

}
