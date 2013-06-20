package com.timetabling.server.ttprinting;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.timetabling.server.data.entities.timetabling.lesson.Lesson;
import com.timetabling.server.data.entities.timetabling.tt.GroupTT;
import com.timetabling.server.data.entities.timetabling.tt.TeacherTT;
import com.timetabling.shared.enums.LessonType;

/**

 */
public class PDFMaker {

  private static final int LOWER_WEEK = 0;
	private static final int TOP_WEEK = 1;

	private Document document = null;
	private Map<Integer, Font> fonts = new HashMap<>();

	// main method
	public void createPDF(List<GroupTT> lessonsGroup,
			List<TeacherTT> lessonsTeacher) throws Exception {

		document = new Document();


			PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));

			document.open();

			// font initialization
			BaseFont unicode = BaseFont.createFont("ARIALUNI.TTF",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			initFonts(unicode);

			// make title
			Font font = fonts.get(12);
			Paragraph preface = new Paragraph("Расписание", font);
			preface.setAlignment(Element.ALIGN_CENTER);
			document.add(preface);

			addEmptyLine(3);

			for (GroupTT group : lessonsGroup) {

				int size = group.getDays().length;

				byte groupNumber = group.getGroupNumber();

				String specialtyName = group.getLessons().get(0)
						.getCurriculumCell().getSpecialtyName();

				byte course = group.getLessons().get(0).getCurriculumCell()
						.getCourse();

				for (int i = 0; i < size / 2; i++) {
					makeDayGroupTable(group.getDays()[i], LOWER_WEEK,
							specialtyName, course, groupNumber);
					makeDayGroupTable(group.getDays()[i + size / 2], TOP_WEEK,
							specialtyName, course, groupNumber);
					document.newPage();
				}
			}

			document.close();

	
	}

	private void makeDayGroupTable(SortedSet<Lesson> lessons, int weekType,
			String specialtyName, byte course, byte groupNumber)
			throws DocumentException {
		String typeWeekString = null;
		if (weekType == LOWER_WEEK) {
			typeWeekString = "Нижняя неделя";
		}

		if (weekType == TOP_WEEK) {
			typeWeekString = "Верхняя неделя";
		}

		if (typeWeekString == null) {
			typeWeekString = "";
		}

		String tableTitleString = typeWeekString + " " + " Курс: " + course
				+ " Специальность: " + specialtyName + " Группа: "
				+ groupNumber;

		document.add(new Paragraph(tableTitleString, fonts.get(12)));
		addEmptyLine(1);

		PdfPTable table = new PdfPTable(3);
		table.addCell("Time");
		table.addCell("Name");
		table.addCell("Type");
		Font font = fonts.get(11);
		for (Lesson lesson : lessons) {
			PdfPCell cellTime = new PdfPCell(new Phrase(lesson.getTime()
					.toString(), font));
			PdfPCell cellLessonName = new PdfPCell(new Phrase(lesson
					.getCurriculumCell().getDisplayName(), font));
			
			PdfPCell cellType = new PdfPCell(new Phrase(
					LessonType.getByCode(lesson.getCurriculumCell()
							.getLessonTypeCode()).getDisplayName(), font));
			table.addCell(cellTime);
			table.addCell(cellLessonName);
			table.addCell(cellType);
		}
		document.add(table);
		addEmptyLine(3);

	}

	private void initFonts(BaseFont unicode) {
		fonts.put(8, new Font(unicode, 8));
		fonts.put(9, new Font(unicode, 9));
		fonts.put(10, new Font(unicode, 10));
		fonts.put(11, new Font(unicode, 11));
		fonts.put(12, new Font(unicode, 12));
		fonts.put(13, new Font(unicode, 13));
		fonts.put(14, new Font(unicode, 14));
		fonts.put(15, new Font(unicode, 15));
		fonts.put(16, new Font(unicode, 16));
	}

	private void addEmptyLine(int number) throws DocumentException {

		if (document == null)
			return;

		for (int i = 0; i < number; i++) {
			document.add(new Paragraph(" "));
		}
	}
}
