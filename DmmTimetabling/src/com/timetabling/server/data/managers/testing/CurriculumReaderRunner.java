package com.timetabling.server.data.managers.testing;

import java.io.File;

import com.timetabling.server.curriculum.reading.CurriculumReader;

public class CurriculumReaderRunner {
	
	public void runReading() throws Exception {
		new CurriculumReader(new File("Уч_план_прикл_2_4_NEW.xls"), 2013,  true).readAndPersistCurriculum();
	}

}
