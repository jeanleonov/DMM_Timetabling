package com.timetabling.server.ttprinting;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
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
	private FileService fileService = null;
	private FileWriteChannel writeChannel = null;
	private AppEngineFile file = null;
	private OutputStream outputStream = null;
	private Map<Integer, Font> fonts = new HashMap<Integer, Font>();

	public BlobKey createPDF(List<GroupTT> lessonsGroup, List<TeacherTT> lessonsTeacher) throws Exception {
		openDocument("timetable.pdf");
		initFonts();
		makeTitle();
		addEmptyLines(3);
		for (GroupTT groupTT : lessonsGroup)
			writeGroupTT(groupTT);
		closeEverything();
		return fileService.getBlobKey(file);
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
		addEmptyLines(1);

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
		addEmptyLines(3);

	}
	
	private void openDocument(String fileName) throws IOException, DocumentException {
		document = new Document();
		fileService = FileServiceFactory.getFileService();
		file = fileService.createNewBlobFile("application/pdf", fileName);
		writeChannel = fileService.openWriteChannel(file, true);
		outputStream = Channels.newOutputStream(writeChannel);
		PdfWriter.getInstance(document, outputStream);
		document.open();
	}
	
	private void closeEverything() throws IOException {
		document.close();
		outputStream.close();
		writeChannel.closeFinally();
	}

	private void initFonts() throws DocumentException, IOException {
		BaseFont unicode = BaseFont.createFont("arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
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
	
	private void makeTitle() throws DocumentException {
		Font font = fonts.get(12);
		Paragraph preface = new Paragraph("Расписание", font);
		preface.setAlignment(Element.ALIGN_CENTER);
		document.add(preface);
	}

	private void addEmptyLines(int number) throws DocumentException {
		for (int i = 0; i < number; i++)
			document.add(new Paragraph(" "));
	}
	
	private void writeGroupTT(GroupTT groupTT) throws DocumentException {
		int size = groupTT.getDays().length;
		byte groupNumber = groupTT.getGroupNumber();
		String specialtyName = groupTT.getSpecialtyName();
		byte course = groupTT.getCourse();
		for (int i = 0; i < size / 2; i++) {
			makeDayGroupTable(groupTT.getDays()[i], LOWER_WEEK,
					specialtyName, course, groupNumber);
			makeDayGroupTable(groupTT.getDays()[i + size / 2], TOP_WEEK,
					specialtyName, course, groupNumber);
			document.newPage();
		}
	}
}
