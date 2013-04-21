package com.timetabling.server.curriculum.reading;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.managers.DaoFactory;
import com.timetabling.server.data.managers.curriculum.CurriculumSaver;
import com.timetabling.server.data.managers.simple.Utils;
import com.timetabling.shared.enums.LessonType;

public class CurriculumReader {

	private File file;

	/** @see CurriculumSaver#saveCurriculumsForSemester(int, boolean, List) */
	private int year;
	/** Use {@link Utils#AUTUMN_WINTER} and {@link Utils#WINTER_SUMMER} */
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

	private InputStream inputstream = null;
	private Workbook wb = null;
	private Sheet sheet = null;
	private List<Cell> curses = null;

	/**
	 * Should read {@link #file} and initiate {@link #curriculumCells}
	 * @throws Exception 
	 */
	
	public static void main(String[] args) throws Exception {
		new CurriculumReader(new File("test1.xls"), 2012,  true).read();

	}

	private void read() throws Exception {
		try {
			inputstream = new FileInputStream(file);
			wb = new HSSFWorkbook(inputstream);
			sheet = wb.getSheetAt(0);

			Row currentrow = null;
			Cell currentcell = null;

			// ================================================
			// ============= Valid Check ======================
			// ================================================
			IOException exception = new IOException(
					"File has the wrong structure");
			currentrow = sheet.getRow(0);
			currentcell = currentrow.getCell(1);
			if (currentcell == null)
				throw exception;
			if (currentcell.getCellType() != Cell.CELL_TYPE_STRING) {
				throw exception;
			}

			String str = currentcell.getStringCellValue();
			if (!str.matches("(.*)Назва( +)дисципліни(.*)")) {
				throw exception;
			}

			curriculumCells = new ArrayList<CurriculumCell>();
			// ================================================
			// ============= parse ============================
			// ================================================
			
			curses = new ArrayList<Cell>();
			currentcell = sheet.getRow(1).getCell(0);
			while((currentcell = findNextCourse(currentcell)) != null) { //get courses
				curses.add(currentcell);
			}
			
			currentrow = sheet.getRow(10);
			int specialcoursesbeginrow = findRowBeginSpecialCurse(); //find first special-course row
			for (int i = 0; i < curses.size(); i++) {
				currentrow = sheet.getRow(10);
				System.out.println("======================================== "+i);
				while ((currentrow = getNextRowSubject(currentrow)) != null) {
					int rowindex = curses.get(i).getRowIndex(); 
					int columnindex = curses.get(i).getColumnIndex();
					byte cursenum = getCourse(curses.get(i)
							.getStringCellValue());
					String specialtyName = file.getName();
					String subjectName = getCellStringValue(currentrow
							.getCell(1));
					rowindex = curses.get(i).getRowIndex();
					if(true) {
						columnindex += 2;
					}
					Cell practices = currentrow.getCell(columnindex+1);
					Cell lecture = currentrow.getCell(columnindex);
					
					String strpractices = getCellStringValue(practices);
					String strlecture = getCellStringValue(lecture);
					
					int intpractices = (strpractices.isEmpty())?0:Integer.parseInt(strpractices.split("\\D")[0]);
					int intlecture = (strlecture.isEmpty())?0:Integer.parseInt(strlecture.split("\\D")[0]);
					
					
					if(lecture.getRowIndex() > specialcoursesbeginrow) {
						curriculumCells.add(new CurriculumCell(specialtyName,subjectName,LessonType.SPECIAL_COURSE,(byte)(intlecture+intpractices),cursenum));
					} else {
						curriculumCells.add(new CurriculumCell(specialtyName,subjectName,LessonType.LECTION,(byte)(intlecture),cursenum));
						curriculumCells.add(new CurriculumCell(specialtyName,subjectName,LessonType.PRACTICE,(byte)(intpractices),cursenum));
					}
					
				}
			}
			System.out.println(curriculumCells); //test
			
		} finally {
			inputstream.close();
		}
	}
	
	/** Find the first line after which there are special courses **/
	private int findRowBeginSpecialCurse() {
		Row row = sheet.getRow(10);
		while(row != null) {
			Cell cell = row.getCell(1);
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				if(cell.getStringCellValue().equals("Дисципліни вільного вибору студентів")) {
					return row.getRowNum();
				}
			}
			row = sheet.getRow(row.getRowNum()+1);
		}
		return Integer.MAX_VALUE;
	}
	
	private static byte getCourse(String coursestr) {
		if(coursestr.matches("^III .*$")) {
			return 3;
		}
		if(coursestr.matches("^IV .*$")) {
			return 4;
		}
		
		if(coursestr.matches("^V .*$")) {
			return 5;
		}
		
		if(coursestr.matches("^II .*$")) {
			return 2;
		}
		
		if(coursestr.matches("^I .*$")) {
			return 1;
		}
		return 0;
	}
	
	private static String getCellStringValue (Cell cell) {
		if(cell == null) return null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return "";
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_ERROR:
			return "Error";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf((int)cell.getNumericCellValue());
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		}
		return null;
	}

	private Row getNextRowSubject(Row currentrow) {
		currentrow = sheet.getRow(currentrow.getRowNum()+1);
		while (currentrow!=null) {
				
			Cell cell = currentrow.getCell(0);
			Cell name = currentrow.getCell(1);
			
			if(cell == null) {
				return null;
			}
			
			
			if(name.getCellType() == Cell.CELL_TYPE_BLANK) { //end plan
				return null;
			}
			
			
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return currentrow;
			} else {
				currentrow = sheet.getRow(currentrow.getRowNum()+1);
				
			}
		}
		return currentrow;

	}
	
	private Cell findNextCourse(Cell from) {
		Row courserow = sheet.getRow(1);
		from = courserow.getCell(from.getColumnIndex()+1);
		while (from != null) {
			if (from.getCellType() == Cell.CELL_TYPE_STRING) {
				String cellvalue = from.getStringCellValue();
				if (cellvalue.matches("^.*((I{1,3})|(IV)|(V)).*$")) {
					return from;
				}
			}
			from = courserow.getCell(from.getColumnIndex()+1);
		}
		return null;
	}

	private void persistCurriculum() throws Exception {
		DaoFactory.getCurriculumSaver().saveCurriculumsForSemester(year,
				season, curriculumCells);
	}

}
