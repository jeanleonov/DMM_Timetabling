package com.timetabling.server.temp;

import java.util.logging.Logger;


public class ReflectMagicTestin {
	
	private static Logger logger = Logger.getLogger( ReflectMagicTestin.class.getSimpleName() );

	public static void doSomthing() {
		try {
//			List<CurriculumCell> cells = new ArrayList<CurriculumCell>();
//			CurriculumCell cell1 = new CurriculumCell("specialty1", "subject1", Type.LECTION, (byte)4);
//			cells.add(cell1);
//			CurriculumCell cell2 = new CurriculumCell("specialty1", "subject2", Type.LECTION, (byte)4);
//			cells.add(cell2);
//			CurriculumCell cell3 = new CurriculumCell("specialty2", "subject3", Type.LECTION, (byte)4);
//			cells.add(cell3);
//			CurriculumCell cell4 = new CurriculumCell("specialty2", "subject2", Type.LECTION, (byte)4);
//			cells.add(cell4);
//			DaoManagerFactory.getCurriculumSaver().saveCurriculumsForSemester(2013, Utils.AUTUMN_WINTER, cells);
			
//			CurriculumExtensionSaver manager = DaoManagerFactory.getCurriculumExtensionSaver();
//			manager.setNumberOfSubgroupsToCurriculumCell(2013, Utils.AUTUMN_WINTER, 7, (byte)2);
			// TODO test this manager.
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
