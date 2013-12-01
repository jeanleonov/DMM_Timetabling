package com.timetabling.client.ui.pages.curriculum.table;

import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.view.client.ProvidesKey;
import com.google.web.bindery.requestfactory.shared.Request;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.base.datagrid.BaseAsyncDataProvider;
import com.timetabling.client.base.datagrid.BaseDataGrid;
import com.timetabling.client.base.datagrid.DataSelectionListener;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.client.communication.entities.CurriculumCellProxy;
import com.timetabling.client.communication.entities.SpecialtyProxy;
import com.timetabling.client.communication.requests.CurriculumCellRequest;
import com.timetabling.shared.enums.LessonType;

public class CurriculumCellGrid extends BaseDataGrid<CurriculumCellProxy> {
	
	public static final int YEAR = Communicator.get().context.getYear();
	public static final boolean SEASON = Communicator.get().context.getSeason();
	private CathedraProxy cathedra = null;
	private SpecialtyProxy specialty = null;
	private Byte course = null;

	public CurriculumCellGrid(DataSelectionListener<CurriculumCellProxy> selectionListener) {
		super(selectionListener);
	}
	
	@Override
	protected void initiateColumns() {
		Column<CurriculumCellProxy, String> cellName = new NameColumn();
		addColumn(cellName, "Имя", "250px");
		Column<CurriculumCellProxy, String> lessonType = new LessonTypeColumn();
		addColumn(lessonType, "Тип пары", "175px");
		Column<CurriculumCellProxy, String> subgroups = new SubgroupsColumn();
		addColumn(subgroups, "Подгруппы", "100px");
		Column<CurriculumCellProxy, String> hours = new HoursColumn();
		addColumn(hours, "Часы", "90px");
		Column<CurriculumCellProxy, String> cathedra = new CathedraColumn();
		addColumn(cathedra, "Кафедра", "175px");
		Column<CurriculumCellProxy, String> specialty = new SpecialtyColumn();
		addColumn(specialty, "Специальность", "175px");
		Column<CurriculumCellProxy, String> subject = new SubjectColumn();
		addColumn(subject, "Предмет", "250px");
		Column<CurriculumCellProxy, String> course = new CourseColumn();
		addColumn(course, "Курс", "60px");
		Column<CurriculumCellProxy, String> teachers = new TeachersColumn();
		addColumn(teachers, "Преподаватели", "300px");
		getElement().getStyle().setWidth(1575, Unit.PX);
	}

	@Override
	protected BaseAsyncDataProvider<CurriculumCellProxy> createDataProvider() {
		return new CurriculumCellAsyncDataProvider();
	}
	
	public CathedraProxy getCathedra() {
		return cathedra;
	}

	public void setCathedra(CathedraProxy cathedra) {
		this.cathedra = cathedra;
	}

	public SpecialtyProxy getSpecialty() {
		return specialty;
	}

	public void setSpecialty(SpecialtyProxy specialty) {
		this.specialty = specialty;
	}

	public Byte getCourse() {
		return course;
	}

	public void setCourse(Byte course) {
		this.course = course;
	}

	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Curriculum.name
	 * */
	public static class NameColumn extends Column<CurriculumCellProxy, String> {
		public NameColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CurriculumCellProxy cell) {
			if (cell.getDisplayName() == null)
				return "";
			return cell.getDisplayName();
		}
	}
	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Curriculum.lessonType
	 * */
	public static class LessonTypeColumn extends Column<CurriculumCellProxy, String> {
		public LessonTypeColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CurriculumCellProxy cell) {
			if (cell.getLessonTypeCode() == -1)
				return "";
			return LessonType.getByCode(cell.getLessonTypeCode()).getDisplayName();
		}
	}
	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Curriculum.subgroupsNumber
	 * */
	public static class SubgroupsColumn extends Column<CurriculumCellProxy, String> {
		public SubgroupsColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CurriculumCellProxy cell) {
			if (cell.getNumberOfSubgroups() == null)
				return "";
			return cell.getNumberOfSubgroups().toString();
		}
	}
	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Curriculum.hours
	 * */
	public static class HoursColumn extends Column<CurriculumCellProxy, String> {
		public HoursColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CurriculumCellProxy cell) {
			if (cell.getHoursInTwoWeeks() == null)
				return "";
			return cell.getHoursInTwoWeeks().toString() + "/2 пары";
		}
	}
	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Curriculum.cathedra
	 * */
	public static class CathedraColumn extends Column<CurriculumCellProxy, String> {
		public CathedraColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CurriculumCellProxy cell) {
			if (cell.getCathedraName() == null)
				return "";
			return cell.getCathedraName();
		}
	}
	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Curriculum.specialty
	 * */
	public static class SpecialtyColumn extends Column<CurriculumCellProxy, String> {
		public SpecialtyColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CurriculumCellProxy cell) {
			return cell.getSpecialtyName();
		}
	}

	/**---------------------------------------------------------- 
	 * Implementation of Column for Curriculum.subject
	 * */
	public static class SubjectColumn extends Column<CurriculumCellProxy, String> {
		public SubjectColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CurriculumCellProxy cell) {
			return cell.getSubjectName();
		}
	}

	/**---------------------------------------------------------- 
	 * Implementation of Column for Curriculum.course
	 * */
	public static class CourseColumn extends Column<CurriculumCellProxy, String> {
		public CourseColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CurriculumCellProxy cell) {
			return ((Byte)cell.getCourse()).toString();
		}
	}

	/**---------------------------------------------------------- 
	 * Implementation of Column for Curriculum.course
	 * */
	public static class TeachersColumn extends Column<CurriculumCellProxy, String> {
		public TeachersColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CurriculumCellProxy cell) {
			// TODO
			return "";
		}
	}
	
	
	/**---------------------------------------------------------- 
	 * Implementation of Asynchronous data provider for Teacher 
	 * */
	public class CurriculumCellAsyncDataProvider extends BaseAsyncDataProvider<CurriculumCellProxy> {
		public CurriculumCellAsyncDataProvider() {}
		@Override
		public ProvidesKey<CurriculumCellProxy> getKeyProvider() {
			return new CurriculumCellKeyProvider();
		}
		@Override
		public Request<List<CurriculumCellProxy>> createListRequest() {
			CurriculumCellRequest request = Communicator.get().requestFactory.createCurriculumCellRequest();
			if (cathedra != null && specialty != null && course != null)
				return request.getCurriculumCellsForCathedraSpecialtyCourse(YEAR, SEASON, cathedra.getId(), specialty.getId(), course)
						.with("cathedraName")
						.with("specialtyName")
						.with("subjectName")
						.with("teachers");
			if (cathedra == null && specialty != null && course != null)
				return request.getCurriculumCellsForSpecialtyCourse(YEAR, SEASON, specialty.getId(), course.byteValue())
						.with("cathedraName")
						.with("specialtyName")
						.with("subjectName")
						.with("teachers");
			if (cathedra != null && specialty == null && course != null)
				return request.getCurriculumCellsForCathedraCourse(YEAR, SEASON, cathedra.getId(), course)
						.with("cathedraName")
						.with("specialtyName")
						.with("subjectName")
						.with("teachers");
			if (cathedra != null && specialty != null && course == null)
				return request.getCurriculumCellsForCathedraSpecialty(YEAR, SEASON, cathedra.getId(), specialty.getId())
						.with("cathedraName")
						.with("specialtyName")
						.with("subjectName")
						.with("teachers");
			if (cathedra != null && specialty == null && course == null)
				return request.getCurriculumCellsForCathedra(YEAR, SEASON, cathedra.getId())
						.with("cathedraName")
						.with("specialtyName")
						.with("subjectName")
						.with("teachers");
			if (cathedra == null && specialty != null && course == null)
				return request.getCurriculumCellsForSpecialty(YEAR, SEASON, specialty.getId())
						.with("cathedraName")
						.with("specialtyName")
						.with("subjectName")
						.with("teachers");
			if (cathedra == null && specialty == null && course != null)
				return request.getCurriculumCellsForCource(YEAR, SEASON, course)
						.with("cathedraName")
						.with("specialtyName")
						.with("subjectName")
						.with("teachers");
			return request.getCurriculumCells(YEAR, SEASON)
					.with("cathedraName")
					.with("specialtyName")
					.with("subjectName")
					.with("teachers");
		}
	}
	

	/**-------------------------------------------- 
	 * Implementation of Key provider for Teacher 
	 * */
	public static class CurriculumCellKeyProvider implements ProvidesKey<CurriculumCellProxy> {
		@Override
		public Object getKey(CurriculumCellProxy item) {
			return item.stableId();
		}
	}
}
