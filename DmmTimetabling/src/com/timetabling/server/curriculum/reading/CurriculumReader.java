package com.timetabling.server.curriculum.reading;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.timetabling.server.data.entities.curriculum.CurriculumCell;
import com.timetabling.server.data.managers.DaoFactory;
import com.timetabling.server.data.managers.curriculum.CurriculumSaver;
import com.timetabling.server.data.managers.simple.SpecialtyManager;
import com.timetabling.server.data.managers.simple.SubjectManager;
import com.timetabling.server.data.managers.simple.Utils;
import com.timetabling.shared.enums.LessonType;

public class CurriculumReader {

	// ================================================
	// ============= Constants ========================
	// ================================================

	// (.*)Назва( +)дисципліни(.*)
	private static final String SUBJECTS_TITLE_TEXT = "(.*)\u041D\u0430\u0437\u0432\u0430"
			+ "( +)\u0434\u0438\u0441\u0446\u0438\u043F\u043B\u0456\u043D\u0438(.*)";

	// Цикл дисциплін вільного вибору студентів
	private static final String SPECIAL_COURSE_TITLE_TEXT = "^(.*)(\u0432\u0456\u043B\u044C\u043D\u043E\u0433\u043E "
			+ "\u0432\u0438\u0431\u043E\u0440\u0443 \u0441\u0442\u0443\u0434\u0435\u043D\u0442\u0456\u0432)(.*)";

	// Військова підготовка
	private static final String INCORRECT_SUBJECT = "\u0412\u0456\u0439\u0441\u044C\u043A\u043E\u0432\u0430 \u043F\u0456\u0434\u0433\u043E\u0442\u043E\u0432\u043A\u0430";

	private static final IOException exception = new IOException(
			"File has the wrong structure");

	// ================================================
	// ============= Variables ========================
	// ================================================

	private File file;

	private InputStream inputStream = null;
	private Workbook workBook = null;
	private Sheet workSheet = null;
	private List<Cell> courses = null;
	private List<CurriculumCell> curriculumCells;
	private Exception mainException = null;
	/** @see CurriculumSaver#saveCurriculumsForSemester(int, boolean, List) */
	private int year;
	/** Use {@link Utils#AUTUMN_WINTER} and {@link Utils#WINTER_SUMMER} */
	private boolean season;

	// ================================================
	// ============= Constructor ======================
	// ================================================
	public CurriculumReader(File file, int year, boolean season) {
		this.file = file;
		this.year = year;
		this.season = season;
	}

	public void readAndPersistCurriculum() throws Exception {
		read();
		persistCurriculum();
	}

	/**
	 * Should read {@link #file} and initiate {@link #curriculumCells}
	 * 
	 * @throws Exception
	 */
	private void read() throws Exception {

		try {
			inputStream = new FileInputStream(file);
			workBook = new HSSFWorkbook(inputStream);
			workSheet = workBook.getSheetAt(0);

			Row currentRow = null;
			Cell currentCell = null;

			// ================================================
			// ============= Valid Check ======================
			// ================================================
			currentRow = workSheet.getRow(0);
			currentCell = currentRow.getCell(1);
			if (currentCell == null)
				throw exception;
			if (currentCell.getCellType() != Cell.CELL_TYPE_STRING) {
				throw exception;
			}

			String str = currentCell.getStringCellValue();
			if (!str.matches(SUBJECTS_TITLE_TEXT)) {
				throw exception;
			}

			curriculumCells = new ArrayList<CurriculumCell>();
			// ================================================
			// ============= parse ============================
			// ================================================

			courses = new ArrayList<Cell>();
			currentCell = workSheet.getRow(1).getCell(0);

			// get list of courses
			while ((currentCell = findNextCourse(currentCell)) != null) {
				courses.add(currentCell);
			}

			currentRow = workSheet.getRow(10);
			// find first special-course row
			int specialCoursesBeginRow = findRowBeginSpecialCurse();
			// iterate through all the courses
			for (int i = 0; i < courses.size(); i++) {

				currentRow = workSheet.getRow(10);

				while ((currentRow = getNextRowSubject(currentRow)) != null) {

					int rowIndex = courses.get(i).getRowIndex();
					int columnIndex = courses.get(i).getColumnIndex();
					// get number of course
					byte cursenum = getCourse(courses.get(i)
							.getStringCellValue());
					// get specialtyName
					String specialtyName = file.getName().split("\\.")[0];
					String subjectName = getCellStringValue(currentRow
							.getCell(1));

					// =================================================
					if (subjectName.equals(INCORRECT_SUBJECT)) {
						continue;
					}
					// =================================================

					rowIndex = courses.get(i).getRowIndex();
					if (season) {
						columnIndex += 2;
					}

					Cell practices = currentRow.getCell(columnIndex + 1);
					Cell lecture = currentRow.getCell(columnIndex);
					if (practices == null || lecture == null) {
						continue;
					}

					String stringPractices = getCellStringValue(practices);
					String stringLecture = getCellStringValue(lecture);

					int intPractices = (stringPractices.isEmpty()) ? 0
							: Integer.parseInt(stringPractices.split("\\D")[0]);
					int intLecture = (stringLecture.isEmpty()) ? 0 : Integer
							.parseInt(stringLecture.split("\\D")[0]);

					if (lecture.getRowIndex() > specialCoursesBeginRow) {
						if (intLecture + intPractices != 0) {
							curriculumCells.add(new CurriculumCell(
									specialtyName, subjectName,
									LessonType.SPECIAL_COURSE,
									(byte) (intLecture + intPractices),
									cursenum));
						}

					} else {
						if (intLecture != 0) {
							curriculumCells.add(new CurriculumCell(
									specialtyName, subjectName,
									LessonType.LECTION, (byte) (intLecture),
									cursenum));
						}

						if (intPractices != 0) {
							curriculumCells.add(new CurriculumCell(
									specialtyName, subjectName,
									LessonType.PRACTICE, (byte) (intPractices),
									cursenum));
						}

					}

				}
			}

		} catch (Exception e) {
			mainException = e;
			throw e;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				if (mainException == null) {
					throw e;
				} else {
					throw mainException;
				}
			}
		}
	}

	/** Find the first line after which there are special courses **/
	private int findRowBeginSpecialCurse() {
		Row row = workSheet.getRow(10);
		while (row != null) {
			Cell cell = row.getCell(1);
			Pattern pattern = Pattern.compile(SPECIAL_COURSE_TITLE_TEXT);

			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				Matcher matcher = pattern.matcher(cell.getStringCellValue());
				if (matcher.matches()) {
					return row.getRowNum();
				}
			}
			row = workSheet.getRow(row.getRowNum() + 1);
		}
		return Integer.MAX_VALUE;
	}

	/** returns the numeric value of course on its string representation **/
	private static byte getCourse(String courseString) {
		if (courseString.matches("^(.*)((III)|(\u0406\u0406\u0406))(.*)$")) {
			return 3;
		}
		if (courseString.matches("^(.*)((IV)|(\u0406V))(.*)$")) {
			return 4;
		}

		if (courseString.matches("^(.*)(V)(.*)$")) {
			return 5;
		}

		if (courseString.matches("^(.*)((II)|(\u0406\u0406))(.*)$")) {
			return 2;
		}

		if (courseString.matches("^(.*)((I)|(\u0406))(.*)$")) {
			return 1;
		}
		return 0;
	}

	private static String getCellStringValue(Cell cell) {
		if (cell == null)
			return null;
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
			return String.valueOf((int) cell.getNumericCellValue());
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		}
		return null;
	}

	private Row getNextRowSubject(Row currentRow) {
		currentRow = workSheet.getRow(currentRow.getRowNum() + 1);
		while (currentRow != null) {

			Cell cell = currentRow.getCell(0);
			Cell name = currentRow.getCell(1);

			if (cell == null) {
				return null;
			}

			if (name.getCellType() == Cell.CELL_TYPE_BLANK) { // end plan
				return null;
			}

			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return currentRow;
			} else {
				currentRow = workSheet.getRow(currentRow.getRowNum() + 1);
			}
		}
		return currentRow;

	}

	private Cell findNextCourse(Cell from) {
		Row courseRow = workSheet.getRow(1);
		from = courseRow.getCell(from.getColumnIndex() + 1);
		while (from != null) {
			if (from.getCellType() == Cell.CELL_TYPE_STRING) {
				String cellValue = from.getStringCellValue();
				if (cellValue
						.matches("^(.*)((\u0406{1,3})|(\u0406V)|(V)|(I{1,3})|(IV))(.*)$")) {
					return from;
				}
			}
			from = courseRow.getCell(from.getColumnIndex() + 1);
		}
		return null;
	}

	 private void persistCurriculum() throws Exception {
	 initForeignKeys(curriculumCells);
	 DaoFactory.getCurriculumSaver().saveCurriculumsForSemester(year,
	 season, curriculumCells);
	 }
	
	 private void initForeignKeys(List<CurriculumCell> cells) throws Exception
	 {
	 SpecialtyManager specialtyManager = DaoFactory.getSpecialtyManager();
	 SubjectManager subjectManager = DaoFactory.getSubjectManager();
	 for (CurriculumCell cell : cells) {
	 cell.setSpecialtyId(specialtyManager.getSpecialtyIdFor(cell
	 .getSpecialtyName()));
	 cell.setSubjectId(subjectManager.getSubjectIdFor(cell
	 .getSubjectName()));
	 }
	 }
}
