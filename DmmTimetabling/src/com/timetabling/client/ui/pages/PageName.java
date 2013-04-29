package com.timetabling.client.ui.pages;

import com.timetabling.client.ui.pages.cathedra.CathedraPage;
import com.timetabling.client.ui.pages.curriculum.CurriculumPage;
import com.timetabling.client.ui.pages.general.info.GeneralInfoPage;
import com.timetabling.client.ui.pages.lessons.LessonsPage;
import com.timetabling.client.ui.pages.specialty.SpecialtyPage;
import com.timetabling.client.ui.pages.subject.SubjectPage;
import com.timetabling.client.ui.pages.teacher.TeacherPage;
import com.timetabling.client.ui.pages.timetable.TimetablePage;
import com.timetabling.client.ui.pages.wishes.WishesPage;


public enum PageName {
	
	SPECIALTIES ("Specialties"),
	SUBJECTS ("Subjects"),
	CATHEDRAS ("Cathedras"),
	TEACHERS ("Teachers"),
	GENERAL_INFO ("General info"),
	WISHES ("Wishes"),
	CURRICULUM_EDITING ("Curriculum editing"),
	LESSONS ("Lessons"),
	TT ("Timetables");
	
	private String name;
	private BasePage page = null;
	private boolean isInitiated = false;
	
	private PageName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public BasePage getPage() {
		if (page == null) {
			if (this == PageName.SPECIALTIES)
				page = new SpecialtyPage();
			else if (this == PageName.SUBJECTS)
				page = new SubjectPage();
			else if (this == PageName.CATHEDRAS)
				page = new CathedraPage();
			else if (this == PageName.TEACHERS)
				page = new TeacherPage();
			else if (this == PageName.GENERAL_INFO)
				page = new GeneralInfoPage();
			else if (this == PageName.WISHES)
				page = new WishesPage();
			else if (this == PageName.CURRICULUM_EDITING)
				page = new CurriculumPage();
			else if (this == PageName.LESSONS)
				page = new LessonsPage();
			else if (this == PageName.TT)
				page = new TimetablePage();
		}
		isInitiated = true;
		return page;
	}

	public boolean isInitiated() {
		return isInitiated;
	}

	public static PageName getByName(String name) {
		for (PageName curName : PageName.values())
			if (curName.getName().equals(name))
				return curName;
		return null;
	}
}
