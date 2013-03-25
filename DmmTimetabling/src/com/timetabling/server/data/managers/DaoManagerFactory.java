package com.timetabling.server.data.managers;

import com.googlecode.objectify.ObjectifyService;
import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.entities.curriculum.Specialty;
import com.timetabling.server.data.entities.curriculum.Subject;
import com.timetabling.server.data.entities.curriculum.Type;
import com.timetabling.server.data.entities.curriculum.extentions.Cathedra;
import com.timetabling.server.data.entities.curriculum.extentions.CurriculumCellJoiner;
import com.timetabling.server.data.entities.curriculum.extentions.Teacher;
import com.timetabling.server.data.entities.curriculum.extentions.Wish;
import com.timetabling.server.data.entities.tt.Lesson;
import com.timetabling.server.data.entities.tt.LessonTimeWithVersion;
import com.timetabling.server.data.entities.tt.Version;


/**
 * DAO Factory registers all required entities in Objectify service Provides
 * access to entity specific managers
 */
public class DaoManagerFactory{
	
	private static CurriculumSaver curriculumSaver = null;
	private static LessonsManager lessonsManager = null;

	static{
		ObjectifyService.register(Cathedra.class);
		ObjectifyService.register(Teacher.class);
		ObjectifyService.register(Wish.class);
		ObjectifyService.register(CurriculumCellJoiner.class);
		ObjectifyService.register(CurriculumCell.class);
		ObjectifyService.register(Specialty.class);
		ObjectifyService.register(Subject.class);
		ObjectifyService.register(Type.class);
		ObjectifyService.register(Version.class);
		ObjectifyService.register(Lesson.class);
		ObjectifyService.register(LessonTimeWithVersion.class);
		lessonsManager = new LessonsManager();
		curriculumSaver = new CurriculumSaver();
	}

	// Static-only usage pattern
	protected DaoManagerFactory() {
	}
	
	public static void initiate() {
	}

	public static LessonsManager getLessonsManager() {
		return lessonsManager;
	}
	
	public static CurriculumSaver getCurriculumSaver() {
		return curriculumSaver;
	}

	
}
