package com.timetabling.client.ui.pages.curriculum;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.base.datagrid.DataSelectionListener;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.client.communication.entities.CurriculumCellProxy;
import com.timetabling.client.communication.entities.SpecialtyProxy;
import com.timetabling.client.communication.providers.CathedraListProvider;
import com.timetabling.client.communication.providers.CourseListProvider;
import com.timetabling.client.communication.providers.HoursListProvider;
import com.timetabling.client.communication.providers.LessonTypeListProvider;
import com.timetabling.client.communication.providers.SpecialtyListProvider;
import com.timetabling.client.communication.providers.SubgroupsListProvider;
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
	@UiField FlowPanel persistedCells;
	
	private SingleSelectList<CathedraProxy> cathedraChoser;
	private SingleSelectList<Byte> courseChoser;
	private SingleSelectList<SpecialtyProxy> specialtyChoser;
	private CathedraProxy cathedra = null;
	private SpecialtyProxy specialty = null;
	private Byte course = null;
	
	private SingleSelectList<CathedraProxy> cathedraSetter;
	private SingleSelectList<Byte> subgroupsSetter;
	private SingleSelectList<Byte> hoursSetter;
	private SingleSelectList<Integer> typeSetter;
	
	private CurriculumCellGrid dataGrid;

	public CurriculumPage() {
		initWidget(uiBinder.createAndBindUi(this));
		createSettersAndChosers();
		addElementsToPanels();
		initDataGrid();
		updateList();
	}
	
	private void createSettersAndChosers() {
		cathedraChoser = new SingleSelectList<CathedraProxy>(new CathedraListProvider(Communicator.get(), new OnCathedraSelect()));
		courseChoser = new SingleSelectList<Byte>(new CourseListProvider(new OnCourseSelect()));
		specialtyChoser = new SingleSelectList<SpecialtyProxy>(new SpecialtyListProvider(Communicator.get(), new OnSpecialtySelect()));
		cathedraSetter = new SingleSelectList<CathedraProxy>(new CathedraListProvider(Communicator.get()));
		subgroupsSetter = new SingleSelectList<Byte>(new SubgroupsListProvider());
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
		form.setVisible(true);
		persistedCells.setVisible(true);
		cathedra = cathedraChoser.getDataProvider().getValue();
		dataGrid.setCathedra(cathedra);
		dataGrid.getProvider().update();
	}
	
	void onCourseSelect() {
		form.setVisible(true);
		persistedCells.setVisible(true);
		course = courseChoser.getDataProvider().getValue();
		dataGrid.setCourse(course);
		dataGrid.getProvider().update();
	}
	
	void onSpecialtySelect() {
		form.setVisible(true);
		persistedCells.setVisible(true);
		specialty = specialtyChoser.getDataProvider().getValue();
		dataGrid.setSpecialty(specialty);
		dataGrid.getProvider().update();
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
		cell.setCathedraId(cathedraSetter.getValue().getId());
		request.putCurriculumCell(CurriculumCellGrid.YEAR, CurriculumCellGrid.SEASON, cell).
		fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("removeButton")
	void onRemove(ClickEvent e) {
		CurriculumCellProxy cell = dataGrid.getSelected();
		CurriculumCellRequest request = Communicator.get().requestFactory.createCurriculumCellRequest();
		request.deleteCell(CurriculumCellGrid.YEAR, CurriculumCellGrid.SEASON, cell.getId()).
		fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
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
		editButton.setVisible(false);
		removeButton.setVisible(false);
		cancelButton.setVisible(false);
		nameSetter.setText("");
	}
	
	private void setEditingMode(CurriculumCellProxy entity) {
		editButton.setVisible(true);
		removeButton.setVisible(true);
		cancelButton.setVisible(true);
		nameSetter.setText(entity.getDisplayName());
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
			nameSetter.setText("");
//			cathedraSetter.setSelectedItem(null);
//			subgroupsSetter.setSelectedItem(null);
//			hoursSetter.setSelectedItem(null);
//			typeSetter.setSelectedItem(null);
		}
	}
	
	private class OnCathedraSelect implements Runnable {
		@Override
		public void run() {
			onCathedraSelect();
		}
	}
	
	private class OnCourseSelect implements Runnable {
		@Override
		public void run() {
			onCourseSelect();
		}
	}
	
	private class OnSpecialtySelect implements Runnable {
		@Override
		public void run() {
			onSpecialtySelect();
		}
	}
}
