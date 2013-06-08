package com.timetabling.server.data.managers.testing;

import java.io.File;

import com.google.appengine.api.ThreadManager;
import com.timetabling.server.curriculum.reading.CurriculumReader;
import com.timetabling.server.generating.Generator;

public class TestRunner {
	
	public void runReading() throws Exception {
		new CurriculumReader(new File("/WEB-INF/Уч_план_прикл_2_4_NEW.xls"), 2013,  true).readAndPersistCurriculum();
	}
	
	public void testAlgorithm() {
		Thread thread = ThreadManager.createBackgroundThread(new Runnable() {
			public void run() {
				try {
					new Generator(2013, true).getTTWithMark(1);
				}
				catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		thread.start();
	}

}
