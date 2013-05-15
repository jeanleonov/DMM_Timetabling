package com.timetabling.client.ui.pages.curriculum;

import java.util.ArrayList;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.base.datagrid.DataSelectionListener;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.client.communication.entities.CurriculumCellProxy;
import com.timetabling.client.communication.entities.SpecialtyProxy;
import com.timetabling.client.communication.entities.TeacherProxy;
import com.timetabling.client.communication.providers.CathedraListProvider;
import com.timetabling.client.communication.providers.CourseListProvider;
import com.timetabling.client.communication.providers.HoursListProvider;
import com.timetabling.client.communication.providers.LessonTypeListProvider;
import com.timetabling.client.communication.providers.SpecialtyListProvider;
import com.timetabling.client.communication.providers.SubgroupsListProvider;
import com.timetabling.client.communication.providers.TeachersListProvider;
import com.timetabling.client.communication.requests.CurriculumCellRequest;
import com.timetabling.client.ui.pages.BasePage;
import com.timetabling.client.ui.pages.curriculum.table.CurriculumCellGrid;
import com.timetabling.client.ui.widgets.chosen.single.SingleSelectList;

public class CurriculumPage extends BasePage implements DataSelectionListener<CurriculumCellProxy> {

	private static CurriculumPageUiBinder uiBinder = GWT
			.create(CurriculumPageUiBinder.class);

	interface CurriculumPageUiBinder extends UiBinder<Widget, CurriculumPage> {
	}

	@UiField Button editButton;
	@UiField Button removeButton;
	@UiField Button cancelButton;
	
	@UiField FlowPanel cathedraChoserContainer;
	@UiField FlowPanel courseChoserContainer;
	@UiField FlowPanel specialtyChoserContainer;
	
	@UiField TextBox nameSetter;
	@UiField FlowPanel cathedraSetterContainer;
	@UiField FlowPanel subgroupsSetterContainer;
	@UiField FlowPanel hoursSetterContainer;
	@UiField FlowPanel typeSetterContainer;
	
	@UiField FlowPanel form;
	@UiField FlowPanel teacher1SetterContainer;
	@UiField FlowPanel teacher2SetterContainer;
	@UiField FlowPanel teacher3SetterContainer;
	@UiField FlowPanel teacher4SetterContainer;
	@UiField FlowPanel persistedCells;
	
	private SingleSelectList<CathedraProxy> cathedraChoser;
	private SingleSelectList<Byte> courseChoser;
	private SingleSelectList<SpecialtyProxy> specialtyChoser;
	private CathedraProxy cathedra = null;
	private SpecialtyProxy specialty = null;
	private Byte course = null;
	
	private CathedraProxy cathedraToSet = null;
	private Byte subgroupToSet = null;
	
	private SingleSelectList<CathedraProxy> cathedraSetter;
	private SingleSelectList<Byte> subgroupsSetter;
	private SingleSelectList<Byte> hoursSetter;
	private SingleSelectList<Integer> typeSetter;
	private List<SingleSelectList<TeacherProxy>> teachersSetters;
	
	private CurriculumCellGrid dataGrid;

	public CurriculumPage() {
		initWidget(uiBinder.createAndBindUi(this));
		teachersSetters = new ArrayList<SingleSelectList<TeacherProxy>>();
		createSettersAndChosers();
		addElementsToPanels();
		initDataGrid();
		updateList();
		setCreatingMode();
	}
	
	private void createSettersAndChosers() {
		cathedraChoser = new SingleSelectList<CathedraProxy>(new CathedraListProvider(Communicator.get(), new OnCathedraSelect()));
		courseChoser = new SingleSelectList<Byte>(new CourseListProvider(new OnCourseSelect()));
		specialtyChoser = new SingleSelectList<SpecialtyProxy>(new SpecialtyListProvider(Communicator.get(), new OnSpecialtySelect()));
		cathedraSetter = new SingleSelectList<CathedraProxy>(new CathedraListProvider(Communicator.get(), new OnCathedraSet()));
		subgroupsSetter = new SingleSelectList<Byte>(new SubgroupsListProvider(new OnSubgroupsSet()));
		hoursSetter = new SingleSelectList<Byte>(new HoursListProvider());
		typeSetter = new SingleSelectList<Integer>(new LessonTypeListProvider());
	}
	
	private void addElementsToPanels() {
		cathedraChoserContainer.add(cathedraChoser);
		courseChoserContainer.add(courseChoser);
		specialtyChoserContainer.add(specialtyChoser);
		cathedraSetterContainer.add(cathedraSetter);
		subgroupsSetterContainer.add(subgroupsSetter);
		hoursSetterContainer.add(hoursSetter);
		typeSetterContainer.add(typeSetter);
	}
	
	private void initDataGrid() {
		dataGrid = new CurriculumCellGrid(this);
		dataGrid.setPreSucces(new PostLoadedAction());
		persistedCells.add(dataGrid);
		dataGrid.getElement().getStyle().setHeight(100, Unit.PCT);
	}
	
	void onCathedraSelect() {
		cathedra = cathedraChoser.getDataProvider().getValue();
		dataGrid.setCathedra(cathedra);
		dataGrid.getProvider().update();
	}
	
	void onCourseSelect() {
		course = courseChoser.getDataProvider().getValue();
		dataGrid.setCourse(course);
		dataGrid.getProvider().update();
	}
	
	void onSpecialtySelect() {
		specialty = specialtyChoser.getDataProvider().getValue();
		dataGrid.setSpecialty(specialty);
		dataGrid.getProvider().update();
	}
	
	void onSubgroupsSet() {
		subgroupToSet = subgroupsSetter.getDataProvider().getValue();
		if (subgroupToSet == null) {
			cleanTeachersSetters();
			return;
		}
		if (cathedraToSet == null)
			return;
		createTeachersSetters(subgroupToSet);
	}
	
	void onCathedraSet() {
		cathedraToSet = cathedraSetter.getDataProvider().getValue();
		if (cathedraToSet == null) {
			cleanTeachersSetters();
			return;
		}
		if (subgroupToSet == null)
			return;
		createTeachersSetters(subgroupToSet);
	}
	
	private void createTeachersSetters(byte subgroupToSet) {
		cleanTeachersSetters();
		teachersSetters.clear();
		if (subgroupToSet >= 1) {
			teacher1SetterContainer.add(new Label("Преподаватель 1-й подгруппы:"));
			teachersSetters.add(
					new SingleSelectList<TeacherProxy>(
							new TeachersListProvider(Communicator.get(), cathedraToSet)));
			teacher1SetterContainer.add(teachersSetters.get(0));
		}
		if (subgroupToSet >= 2) {
			teacher2SetterContainer.add(new Label("Преподаватель 2-й подгруппы:"));
			teachersSetters.add(
					new SingleSelectList<TeacherProxy>(
							new TeachersListProvider(Communicator.get(), cathedraToSet)));
			teacher2SetterContainer.add(teachersSetters.get(1));
		}
		if (subgroupToSet >= 3) {
			teacher3SetterContainer.add(new Label("Преподаватель 3-й подгруппы:"));
			teachersSetters.add(
					new SingleSelectList<TeacherProxy>(
							new TeachersListProvider(Communicator.get(), cathedraToSet)));
			teacher3SetterContainer.add(teachersSetters.get(2));
		}
		if (subgroupToSet >= 4) {
			teacher4SetterContainer.add(new Label("Преподаватель 4-й подгруппы:"));
			teachersSetters.add(
					new SingleSelectList<TeacherProxy>(
							new TeachersListProvider(Communicator.get(), cathedraToSet)));
			teacher4SetterContainer.add(teachersSetters.get(3));
		}
	}

	@UiHandler("editButton")
	void onEdit(ClickEvent e) {
		CurriculumCellProxy cell = dataGrid.getSelected();
		CurriculumCellRequest request = Communicator.get().requestFactory.createCurriculumCellRequest();
		cell = request.edit(cell);
		cell.setDisplayName(nameSetter.getValue());
		cell.setNumberOfSubgroups(subgroupsSetter.getValue());
		cell.setHoursInTwoWeeks(hoursSetter.getValue());
		cell.setLessonTypeCode(typeSetter.getValue());
		CathedraProxy cathedra = cathedraSetter.getValue();
		cell.setCathedraId(cathedra==null? null : cathedra.getId());
		request.putCurriculumCell(CurriculumCellGrid.YEAR, CurriculumCellGrid.SEASON, cell);
		for (byte i=1; i<=teachersSetters.size(); i++) {
			TeacherProxy teacher = teachersSetters.get(i-1).getValue();
			if (teacher != null)
				request.setTeacherForLesson(CurriculumCellGrid.YEAR, CurriculumCellGrid.SEASON, cell.getId(), i, teacher.getId());
		}
		request.fire(new UpdateReceiver());
	}

	@UiHandler("removeButton")
	void onRemove(ClickEvent e) {
		CurriculumCellProxy cell = dataGrid.getSelected();
		CurriculumCellRequest request = Communicator.get().requestFactory.createCurriculumCellRequest();
		request.deleteCell(CurriculumCellGrid.YEAR, CurriculumCellGrid.SEASON, cell.getId()).
		fire(new UpdateReceiver());
	}

	@UiHandler("cancelButton")
	void onCancel(ClickEvent e) {
		CurriculumCellProxy cell = dataGrid.getSelected();
		dataGrid.getSelectionModel().setSelected(cell, false);
		setCreatingMode();
	}
	
	@UiHandler("updateButton")
	void onUpdate(ClickEvent e) {
		dataGrid.getProvider().update();
	}
	
	private void setCreatingMode() {
		nameSetter.setEnabled(false);
		cathedraSetter.setEnabled(false);
		subgroupsSetter.setEnabled(false);
		hoursSetter.setEnabled(false);
		typeSetter.setEnabled(false);
		editButton.setEnabled(false);
		removeButton.setEnabled(false);
		cancelButton.setEnabled(false);
		cleanTeachersSetters();
	}
	
	private void cleanTeachersSetters() {
		teacher1SetterContainer.clear();
		teacher2SetterContainer.clear();
		teacher3SetterContainer.clear();
		teacher4SetterContainer.clear();
	}
	
	private void setEditingMode(CurriculumCellProxy entity) {
		nameSetter.setText(entity.getDisplayName());
		((CathedraListProvider) cathedraSetter.getDataProvider()).setSelectedCathedraName(entity.getCathedraName());
		subgroupsSetter.setSelectedItem(entity.getNumberOfSubgroups());
		hoursSetter.setSelectedItem(entity.getHoursInTwoWeeks());
		typeSetter.setSelectedItem(entity.getLessonTypeCode());
		editButton.setEnabled(true);
		removeButton.setEnabled(true);
		cancelButton.setEnabled(true);
		nameSetter.setEnabled(true);
		cathedraSetter.setEnabled(true);
		subgroupsSetter.setEnabled(true);
		hoursSetter.setEnabled(true);
		typeSetter.setEnabled(true);
	}
	
	private void updateList() {
		dataGrid.setCathedra(cathedra);
		dataGrid.setSpecialty(specialty);
		dataGrid.setCourse(course);
		dataGrid.getProvider().update();
		setCreatingMode();
	}

	@Override
	public void onRowSelected(CurriculumCellProxy entity) {
		setEditingMode(entity);
	}
	
	
	/**---------------------------------------------------------- 
	 * What will be done when data is loaded
	 * */
	private class PostLoadedAction implements Runnable {
		@Override
		public void run() {
		}
	}
	
	private class OnCathedraSelect implements Runnable {
		@Override public void run()   { onCathedraSelect(); }
	}
	private class OnCourseSelect implements Runnable {
		@Override public void run()   { onCourseSelect(); }
	}
	private class OnSpecialtySelect implements Runnable {
		@Override public void run()   { onSpecialtySelect(); }
	}
	private class OnCathedraSet implements Runnable {
		@Override public void run()   { onCathedraSet(); }
	}
	private class OnSubgroupsSet implements Runnable {
		@Override public void run()   { onSubgroupsSet(); }
	}
	private class UpdateReceiver extends Receiver<Void> {
		@Override public void onSuccess(Void response)   { updateList(); }
	}
}
