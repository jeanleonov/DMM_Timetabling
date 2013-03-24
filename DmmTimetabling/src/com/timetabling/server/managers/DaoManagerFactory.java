package com.rosinka.tt.server.managers;

import com.googlecode.objectify.ObjectifyService;
import com.rosinka.tt.server.entities.curriculum.CurriculumCell;
import com.rosinka.tt.server.entities.curriculum.Specialty;
import com.rosinka.tt.server.entities.curriculum.Subject;
import com.rosinka.tt.server.entities.curriculum.Type;
import com.rosinka.tt.server.entities.curriculum.extentions.Cathedra;
import com.rosinka.tt.server.entities.curriculum.extentions.CurriculumCellJoiner;
import com.rosinka.tt.server.entities.curriculum.extentions.Teacher;
import com.rosinka.tt.server.entities.curriculum.extentions.Wish;
import com.rosinka.tt.server.entities.tt.Lesson;
import com.rosinka.tt.server.entities.tt.LessonTimeWithVersion;
import com.rosinka.tt.server.entities.tt.Version;


/**
 * DAO Factory registers all required entities in Objectify service Provides
 * access to entity specific managers
 */
public class DaoManagerFactory{

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
	}

	// Static-only usage pattern
	protected DaoManagerFactory(){
	}

	public static void initiate(){
	}

	public static LessonManager getLessonManager(){
		return new LessonManager();
	}
	// TODO
}
