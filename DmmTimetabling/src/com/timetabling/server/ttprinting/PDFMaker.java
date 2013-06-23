package com.timetabling.server.ttprinting;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.List;
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
	private static String[] COLUMNS = new String[]{"Time", "Name", "Type", "Teacher"};
	private Font TABLE_TITLE_FONT;
	private Font TABLE_CELL_FONT;
	private Font DOCUMENT_TITLE_FONT;

	private Document document;
	private FileService fileService;
	private FileWriteChannel writeChannel;
	private AppEngineFile file;
	private OutputStream outputStream;
	
	public PDFMaker() throws DocumentException, IOException {
		BaseFont unicode = BaseFont.createFont("arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		TABLE_TITLE_FONT = new Font(unicode, 12);
		DOCUMENT_TITLE_FONT = new Font(unicode, 12);
		TABLE_CELL_FONT = new Font(unicode, 11);
	}

	public BlobKey createPDF(List<GroupTT> groupTTs, List<TeacherTT> teacherTTs) throws Exception {
		openDocument("timetable.pdf");
		addDocumentTitle();
		addEmptyLines(3);
		for (GroupTT groupTT : groupTTs) {
			writeGroupTT(groupTT);
			document.newPage();
		}
		closeEverything();
		return fileService.getBlobKey(file);
	}
	
	private void writeGroupTT(GroupTT groupTT) throws DocumentException {
		byte group = groupTT.getGroupNumber();
		String specialty = groupTT.getSpecialtyName();
		byte course = groupTT.getCourse();
		makeGroupWeekTable(groupTT.getDays(), LOWER_WEEK, specialty, course, group);
		makeGroupWeekTable(groupTT.getDays(), TOP_WEEK, specialty, course, group);
	}

	private void makeGroupWeekTable(SortedSet<Lesson>[] days, int weekCode,
									String specialty, byte course, byte group)
									throws DocumentException {
		addTableTitle(weekCode, course, specialty, group);
		PdfPTable table = getTableWithColumns(COLUMNS);
		int start = weekCode==LOWER_WEEK? 0 : days.length/2;
		int end = weekCode==LOWER_WEEK? days.length/2 : days.length;
		for (int i=start; i<end; i++)
			for (Lesson lesson : days[i])
				fillTableRow(table, lesson);
		document.add(table);
		addEmptyLines(3);
	}
	
	private void fillTableRow(PdfPTable table, Lesson lesson) {
		PdfPCell cellTime = getTimeCellFor(lesson);
		PdfPCell cellLessonName = getLessonNameCellFor(lesson);
		PdfPCell cellType = getLessonTypeCellFor(lesson);
		PdfPCell cellTeacher = getTeacherCellFor(lesson);
		table.addCell(cellTime);
		table.addCell(cellLessonName);
		table.addCell(cellType);
		table.addCell(cellTeacher);
	}
	
	private PdfPCell getTimeCellFor(Lesson lesson) {
		String time = lesson.getTime().toString();
		Phrase phrase = new Phrase(time, TABLE_CELL_FONT);
		return new PdfPCell(phrase);
	}
	
	private PdfPCell getLessonNameCellFor(Lesson lesson) {
		String name = lesson.getCurriculumCell().getDisplayName();
		Phrase phrase = new Phrase(name, TABLE_CELL_FONT);
		return new PdfPCell(phrase);
	}
	
	private PdfPCell getLessonTypeCellFor(Lesson lesson) {
		int typeCode = lesson.getCurriculumCell().getLessonTypeCode();
		LessonType type = LessonType.getByCode(typeCode);
		String name = type.getDisplayName();
		Phrase phrase = new Phrase(name, TABLE_CELL_FONT);
		return new PdfPCell(phrase);
	}
	
	private PdfPCell getTeacherCellFor(Lesson lesson) {
		String name = lesson.getTeacherTT().getTeacherName();
		Phrase phrase = new Phrase(name, TABLE_CELL_FONT);
		return new PdfPCell(phrase);
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
	
	private void addDocumentTitle() throws DocumentException {
		Paragraph preface = new Paragraph("Расписание", DOCUMENT_TITLE_FONT);
		preface.setAlignment(Element.ALIGN_CENTER);
		document.add(preface);
	}
	
	private void addTableTitle(int weekCode, byte course, String spec, byte group) throws DocumentException {
		StringBuilder result = new StringBuilder();
		String weekType = weekCode==LOWER_WEEK? "Нижняя неделя" : "Верхняя неделя";
		result.append(weekType);
		result.append("   Курс: ").append(course);
		result.append("   Специальность: ").append(spec);
		result.append("   Группа: ").append(group);
		document.add(new Paragraph(result.toString(), TABLE_TITLE_FONT));
		addEmptyLines(1);
	}
	
	private PdfPTable getTableWithColumns(String[] columns) {
		PdfPTable table = new PdfPTable(columns.length);
		for (int i=0; i<columns.length; i++)
			table.addCell(columns[i]);
		return table;
	}

	private void addEmptyLines(int number) throws DocumentException {
		for (int i = 0; i < number; i++)
			document.add(new Paragraph(" "));
	}
}
