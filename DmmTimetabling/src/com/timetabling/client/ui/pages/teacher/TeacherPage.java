package com.timetabling.client.ui.pages.teacher;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.base.datagrid.DataSelectionListener;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.client.communication.entities.TeacherProxy;
import com.timetabling.client.communication.requests.SubjectRequest;
import com.timetabling.client.communication.requests.TeacherRequest;
import com.timetabling.client.ui.pages.BasePage;
import com.timetabling.client.ui.pages.teacher.table.TeacherGrid;

public class TeacherPage extends BasePage implements DataSelectionListener<TeacherProxy> {

	private static TeacherPageUiBinder uiBinder = GWT
			.create(TeacherPageUiBinder.class);

	interface TeacherPageUiBinder extends UiBinder<Widget, TeacherPage> {
	}

	@UiField Button saveButton;
	@UiField Button editButton;
	@UiField Button removeButton;
	@UiField Button cancelButton;
	@UiField TextBox nameSetter;
	@UiField TextBox rankSetter;
	@UiField FlowPanel persistedTeachers;
	private TeacherGrid dataGrid;
	private CathedraProxy cathedra;

	public TeacherPage() {
		initWidget(uiBinder.createAndBindUi(this));
		dataGrid = new TeacherGrid(this);
		dataGrid.setPreSucces(new PostLoadedAction());
		persistedTeachers.add(dataGrid);
		dataGrid.getElement().getStyle().setHeight(100, Unit.PCT);
		updateList();
	}

	@UiHandler("saveButton")
	void onSave(ClickEvent e) {
		TeacherRequest requestContext = Communicator.get().requestFactory.createTeacherRequest();
		TeacherProxy teacher = requestContext.create(TeacherProxy.class);
		teacher.setName(nameSetter.getValue());
		teacher.setRankCode(rankSetter.getValue());
		requestContext.putTeacher(teacher, cathedra.getId()).
		fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("editButton")
	void onEdit(ClickEvent e) {
		TeacherProxy teacher = dataGrid.getSelected();
		TeacherRequest request = Communicator.get().requestFactory.createTeacherRequest();
		teacher = request.edit(teacher);
		teacher.setName(nameSetter.getValue());
		teacher.setRankCode(rankSetter.getValue());
		request.putTeacher(teacher, cathedra.getId()).
		fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("removeButton")
	void onRemove(ClickEvent e) {
		TeacherProxy teacher = dataGrid.getSelected();
		TeacherRequest request = Communicator.get().requestFactory.createTeacherRequest();
		request.deleteTeacher(teacher.getId()).
		fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("cancelButton")
	void onCancel(ClickEvent e) {
		TeacherProxy teacher = dataGrid.getSelected();
		dataGrid.getSelectionModel().setSelected(teacher, false);
		setCreatingMode();
	}
	
	private void setCreatingMode() {
		saveButton.setVisible(true);
		editButton.setVisible(false);
		removeButton.setVisible(false);
		cancelButton.setVisible(false);
		nameSetter.setText("");
		rankSetter.setText("");
	}
	
	private void setEditingMode(TeacherProxy entity) {
		saveButton.setVisible(false);
		editButton.setVisible(true);
		removeButton.setVisible(true);
		cancelButton.setVisible(true);
		nameSetter.setText(entity.getName());
		rankSetter.setValue(entity.getRankCode());
	}
	
	private void updateList() {
		dataGrid.setCathedra(cathedra);
		dataGrid.getProvider().update();
		setCreatingMode();
	}

	@Override
	public void onRowSelected(TeacherProxy entity) {
		setEditingMode(entity);
	}
	
	
	/**---------------------------------------------------------- 
	 * What will be done when data is loaded
	 * */
	private class PostLoadedAction implements Runnable {
		@Override
		public void run() {
			nameSetter.setText("");
			rankSetter.setValue(null);
		}
	}
}
