package com.timetabling.server.curriculum.reading;

import java.io.File;
import java.util.List;

import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.managers.DaoFactory;
import com.timetabling.server.data.managers.curriculum.CurriculumSaver;
import com.timetabling.server.data.managers.simple.Utils;

public class CurriculumReader {
	
	private File file;
	
	/**@see CurriculumSaver#saveCurriculumsForSemester(int, boolean, List)*/
	private int year;
	/** Use {@link Utils#AUTUMN_WINTER} and {@link Utils#WINTER_SUMMER}*/
	private boolean season;
	private List<CurriculumCell> curriculumCells;

	public CurriculumReader(File file, int year, boolean season) {
		this.file = file;
		this.year = year;
		this.season = season;
	}
	
	public void readAndPersistCurriculum() throws Exception {
		read();
		persistCurriculum();
	}
	
	/** Should read {@link #file} and initiate {@link #curriculumCells} */
	private void read() {
		// TODO
	}
	
	private void persistCurriculum() throws Exception {
		DaoFactory.getCurriculumSaver().saveCurriculumsForSemester(year, season, curriculumCells);
	}
	
}
