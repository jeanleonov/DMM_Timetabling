package com.timetabling.server.data.managers.testing;

import java.io.File;

import com.timetabling.server.curriculum.reading.CurriculumReader;

public class CurriculumReaderRunner {
	
	public void runReading() throws Exception {
		new CurriculumReader(new File("Σχολ_θντ_2-4.xls"), 2012,  true).readAndPersistCurriculum();
	}

}
