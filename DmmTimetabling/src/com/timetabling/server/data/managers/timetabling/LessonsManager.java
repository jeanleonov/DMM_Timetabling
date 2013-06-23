package com.timetabling.server.data.managers.timetabling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.timetabling.server.base.data.dao.GenericDAO;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.tt.GroupTT;
import com.timetabling.server.data.entities.timetabling.tt.TeacherTT;
import com.timetabling.server.data.entities.timetabling.tt.TimetableIndividual;
import com.timetabling.server.data.managers.simple.Utils;

public class LessonsManager extends GenericDAO<Lesson> {

	/** Access modifier is DEFAULT here and it is not accident */
	public LessonsManager() {
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
	public List<Lesson> getLessonsFor(Long teacherId, int curYear, boolean season){
		// TODO
		return null;
	}
	
	/**
	 * @param specialtyName
	 * @param year - 1-4 for bachelors and 5 for masters
	 * @param semester - 1 or 2
	 * @param groupNumber
	 * */
	public List<Lesson> getLessonsFor(Long specialtyId, byte course, byte groupNumber, int year, boolean season){
		// TODO
		return null;
	}

	public List<Lesson> getLessonsFor(int year, boolean season) {
		Utils.setNamespaceForSemester(year, season);
		return ofy().query(Lesson.class).list();
	}
	
	public TimetableIndividual bindLessonsToTimetabels(List<Lesson> lessons, List<CurriculumCell> cells, Long version) {
		setCurriculumCellsForLessons(lessons, cells);
		Map<Long, Map<Byte, Byte>> specCourseSubgroupsMap = getGroupsNumbers(cells);
		Map<Long, Map<Byte, Map<Byte, List<Lesson>>>> groupsLessons = getGroupsLessons(lessons, specCourseSubgroupsMap);
		Map<Long, List<Lesson>> teachersLessons = getTeachersLessons(lessons);
		TimetableIndividual tt = createTimetableWithTimes(groupsLessons, teachersLessons, version);
		tt.setAllLessons(lessons);
		return tt;
	}
	
	public void initConnectionsBetweenLessons(TimetableIndividual tt) {
		for (Lesson lesson : tt.getAllLessons()) {
			List<Lesson> lessons = new ArrayList<Lesson>();
			lesson.setLessonsWithMyTeacherOrGroup(lessons);
			lessons.addAll(lesson.getTeacherTT().getLessons());
			for (GroupTT groupTT : lesson.getGroupTTs())
				lessons.addAll(groupTT.getLessons());
		}
	}
	
	private void setCurriculumCellsForLessons(List<Lesson> lessons, List<CurriculumCell> cells) {
		for (Lesson lesson : lessons)
			for (CurriculumCell cell : cells)
				if (lesson.getParent().getId() == cell.getId()) {
					lesson.setCurriculumCell(cell);
					break;
				}
	}
	
	private Map<Long, Map<Byte, Byte>> getGroupsNumbers(List<CurriculumCell> cells) {
		Map<Long, Map<Byte, Byte>> specTo = new HashMap<Long, Map<Byte, Byte>>();
		for (CurriculumCell cell : cells) {
			Long specId = cell.getSpecialtyId();
			Map<Byte, Byte> courseTo = specTo.get(specId); 
			if (courseTo == null) {
				courseTo = new HashMap<Byte, Byte>();
				specTo.put(specId, courseTo);
			}
			Byte numberOfSubgroups = courseTo.get(cell.getCourse());
			if (numberOfSubgroups == null || numberOfSubgroups < cell.getNumberOfSubgroups())
				courseTo.put((Byte) cell.getCourse(), cell.getNumberOfSubgroups());
		}
		return specTo;
	}
	
	private Map<Long, Map<Byte, Map<Byte, List<Lesson>>>> getGroupsLessons(
										List<Lesson> lessons,
										Map<Long, Map<Byte, Byte>> specCourseSubgroupsMap) {
		Map<Long, Map<Byte, Map<Byte, List<Lesson>>>> specTo = new HashMap<Long, Map<Byte,Map<Byte,List<Lesson>>>>();
		for (Lesson lesson : lessons) {
			Long specId = lesson.getCurriculumCell().getSpecialtyId();
			Map<Byte, Map<Byte, List<Lesson>>> courseTo = specTo.get(specId); 
			if (courseTo == null) {
				courseTo = new HashMap<Byte, Map<Byte,List<Lesson>>>();
				specTo.put(specId, courseTo);
			}
			Byte course = lesson.getCurriculumCell().getCourse();
			Map<Byte, List<Lesson>> groupTo = courseTo.get(course); 
			if (groupTo == null) {
				groupTo = new HashMap<Byte,List<Lesson>>();
				courseTo.put(course, groupTo);
			}
			Byte group = lesson.getSubGroupNumber();
			if (lesson.getCurriculumCell().getNumberOfSubgroups() == specCourseSubgroupsMap.get(specId).get(course)) {
				List<Lesson> groupsLessons = groupTo.get(group);
				if (groupsLessons == null) {
					groupsLessons = new ArrayList<Lesson>();
					groupTo.put(group, groupsLessons);
				}
				groupsLessons.add(lesson);
			}
			else {
				for (Byte groupNum=1; groupNum<=lesson.getCurriculumCell().getNumberOfSubgroups(); groupNum++) {
					List<Lesson> groupsLessons = groupTo.get(groupNum);
					if (groupsLessons == null) {
						groupsLessons = new ArrayList<Lesson>();
						groupTo.put(groupNum, groupsLessons);
					}
					groupsLessons.add(lesson);
				}
			}
		}
		return specTo;
	}
	
	private Map<Long, List<Lesson>> getTeachersLessons(List<Lesson> lessons) {
		Map<Long, List<Lesson>> teacherTo = new HashMap<Long, List<Lesson>>();
		for (Lesson lesson : lessons) {
			Long teacherId = lesson.getTeacherId();
			List<Lesson> teachersLessons = teacherTo.get(teacherId); 
			if (teachersLessons == null) {
				teachersLessons = new ArrayList<Lesson>();
				teacherTo.put(teacherId, teachersLessons);
			}
			teachersLessons.add(lesson);
		}
		return teacherTo;
	}
	
	private TimetableIndividual createTimetableWithTimes(
									Map<Long, Map<Byte, Map<Byte, List<Lesson>>>> specialtyTo,
									Map<Long, List<Lesson>> teacherTo,
									Long version) {
		TimetableIndividual tt = new TimetableIndividual();
		for (Long specialtyId : specialtyTo.keySet()) {
			Map<Byte, Map<Byte, List<Lesson>>> courseTo = specialtyTo.get(specialtyId);
			for (Byte course : courseTo.keySet()) {
				Map<Byte, List<Lesson>> groupTo = courseTo.get(course);
				for (Byte group : groupTo.keySet()) {
					List<Lesson> lessons = groupTo.get(group);
					GroupTT groupTT = new GroupTT(lessons, version, specialtyId, course, group);
					tt.addGroupTT(groupTT);
				}
			}
		}
		for (Long teacherId : teacherTo.keySet()) {
			List<Lesson> lessons = teacherTo.get(teacherId);
			TeacherTT teacherTT = new TeacherTT(lessons, version);
			tt.addTeacherTT(teacherTT);
		}
		return tt;
	}

}
