package com.timetabling.server.data.managers;

import com.googlecode.objectify.ObjectifyService;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.curriculum.Specialty;
import com.timetabling.server.data.entities.curriculum.Subject;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;
import com.timetabling.server.data.entities.curriculum.extentions.CurriculumCellJoiner;
import com.timetabling.server.data.entities.curriculum.extentions.RoomsInfo;
import com.timetabling.server.data.entities.curriculum.extentions.Teacher;
import com.timetabling.server.data.entities.curriculum.extentions.Wish;
import com.timetabling.server.data.entities.timetabling.Version;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.lesson.LessonTimeWithVersion;
import com.timetabling.server.data.entities.timetabling.rule.RulePriority;
import com.timetabling.server.data.managers.curriculum.CurriculumExtensionSaver;
import com.timetabling.server.data.managers.curriculum.CurriculumSaver;
import com.timetabling.server.data.managers.simple.CathedraManager;
import com.timetabling.server.data.managers.simple.CurriculumCellJoinerManager;
import com.timetabling.server.data.managers.simple.CurriculumCellManager;
import com.timetabling.server.data.managers.simple.RoomsInfoManager;
import com.timetabling.server.data.managers.simple.RulePriorityManager;
import com.timetabling.server.data.managers.simple.SpecialtyManager;
import com.timetabling.server.data.managers.simple.SubjectManager;
import com.timetabling.server.data.managers.simple.TeacherManager;
import com.timetabling.server.data.managers.timetabling.LessonsManager;


/**
 * DAO Factory registers all required entities in Objectify service Provides
 * access to entity specific managers
 */
public class DaoFactory {
	
	private static CurriculumSaver curriculumSaver = null;
	private static CurriculumExtensionSaver curriculumExtensionSaver = null;
	private static CathedraManager cathedraManager = null;
	private static CurriculumCellJoinerManager curriculumCellJoinerManager = null;
	private static CurriculumCellManager curriculumCellManager = null;
	private static RoomsInfoManager roomsInfoManager = null;
	private static RulePriorityManager rulePriorityManager = null;
	private static SpecialtyManager specialtyManager = null;
	private static SubjectManager subjectManager = null;
	private static TeacherManager teacherManager = null;
	private static LessonsManager lessonsManager = null;

	static {
		ObjectifyService.register(Cathedra.class);
		ObjectifyService.register(CurriculumCellJoiner.class);
		ObjectifyService.register(RoomsInfo.class);
		ObjectifyService.register(Teacher.class);
		ObjectifyService.register(Wish.class);
		ObjectifyService.register(CurriculumCell.class);
		ObjectifyService.register(Specialty.class);
		ObjectifyService.register(Subject.class);
		ObjectifyService.register(Lesson.class);
		ObjectifyService.register(LessonTimeWithVersion.class);
		ObjectifyService.register(RulePriority.class);
		ObjectifyService.register(Version.class);
		curriculumSaver = new CurriculumSaver();
		curriculumExtensionSaver = new CurriculumExtensionSaver();
		cathedraManager = new CathedraManager();
		curriculumCellJoinerManager = new CurriculumCellJoinerManager();
		curriculumCellManager = new CurriculumCellManager();
		roomsInfoManager = new RoomsInfoManager();
		rulePriorityManager = new RulePriorityManager();
		specialtyManager = new SpecialtyManager();
		subjectManager = new SubjectManager();
		teacherManager = new TeacherManager();
		lessonsManager = new LessonsManager();
	}

	// Static-only usage pattern
	protected DaoFactory() {
	}
	
	public static void initiate() {
	}

	public static CurriculumSaver getCurriculumSaver() {
		return curriculumSaver;
	}

	public static CurriculumExtensionSaver getCurriculumExtensionSaver() {
		return curriculumExtensionSaver;
	}

	public static CathedraManager getCathedraManager() {
		return cathedraManager;
	}

	public static CurriculumCellJoinerManager getCurriculumCellJoinerManager() {
		return curriculumCellJoinerManager;
	}

	public static CurriculumCellManager getCurriculumCellManager() {
		return curriculumCellManager;
	}

	public static RoomsInfoManager getRoomsInfoManager() {
		return roomsInfoManager;
	}

	public static RulePriorityManager getRulePriorityManager() {
		return rulePriorityManager;
	}

	public static SpecialtyManager getSpecialtyManager() {
		return specialtyManager;
	}

	public static SubjectManager getSubjectManager() {
		return subjectManager;
	}

	public static TeacherManager getTeacherManager() {
		return teacherManager;
	}

	public static LessonsManager getLessonsManager() {
		return lessonsManager;
	}

	
}
