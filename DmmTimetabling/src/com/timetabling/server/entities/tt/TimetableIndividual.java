package com.rosinka.tt.server.entities.tt;

import java.util.List;

import com.rosinka.tt.server.exceptions.CurriculumIsNotEnoughExtenedException;

public class TimetableIndividual {
	
	/** year - '2012' <=> academic year 2012-2013  */
	private short year;
	
	/** semester -	'1' <=> 1_September_2012 - 1_January_2013,
	 * 				'2' <=> 1_January_2013   - 1_July_2013 */
	private byte semester;
	
	private Long activeVersion;
	private List<GroupTT> groupTTs;
	private List<TeacherTT> teacherTTs;
	private List<Lesson> allLessons;

	/**
	 * GroupTTs, TeacherTTs and allLessons will automatically load into
	 * @param curYear - '2012' <=> academic year 2012-2013
	 * @param curSemester -	'1' <=> 1_September_2012 - 1_January_2013,
	 * 						'2' <=> 1_January_2013   - 1_July_2013
	 * @exception throws if Curriculum is not enough extended
	 * */
	public TimetableIndividual(short year, byte semester) throws CurriculumIsNotEnoughExtenedException{
		// TODO
	}
	
	public void switchToVersion(Long version){
		// TODO
	}
	
	public void switchToLastVersion(){
		// TODO
	}

	public short getYear() {
		return year;
	}

	public byte getSemester() {
		return semester;
	}

	public Long getActiveVersion() {
		return activeVersion;
	}

	public List<GroupTT> getGroupTTs() {
		return groupTTs;
	}

	public List<TeacherTT> getTeacherTTs() {
		return teacherTTs;
	}

	public List<Lesson> getAllLessons() {
		return allLessons;
	}
	
	/**
	 * @return number of FREE computer rooms at target time
	 */
	public int getNumberOfFreeComputerRooms(Time time){
		// TODO
		return 0;
	}
}
