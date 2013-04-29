package com.timetabling.client.ui.pages.subject;

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
import com.timetabling.client.communication.entities.SubjectProxy;
import com.timetabling.client.communication.requests.SubjectRequest;
import com.timetabling.client.ui.pages.BasePage;
import com.timetabling.client.ui.pages.subject.table.SubjectGrid;

public class SubjectPage extends BasePage implements DataSelectionListener<SubjectProxy> {

	private static SubjectPageUiBinder uiBinder = GWT
			.create(SubjectPageUiBinder.class);

	interface SubjectPageUiBinder extends UiBinder<Widget, SubjectPage> {
	}

	@UiField Button saveButton;
	@UiField Button editButton;
	@UiField Button removeButton;
	@UiField Button cancelButton;
	@UiField TextBox nameSetter;
	@UiField TextBox displayNameSetter;
	@UiField FlowPanel persistedSubjects;
	private SubjectGrid dataGrid;

	public SubjectPage() {
		initWidget(uiBinder.createAndBindUi(this));
		dataGrid = new SubjectGrid(this);
		dataGrid.setPreSucces(new PostLoadedAction());
		persistedSubjects.add(dataGrid);
		dataGrid.getElement().getStyle().setHeight(100, Unit.PCT);
		updateList();
	}

	@UiHandler("saveButton")
	void onSave(ClickEvent e) {
		SubjectRequest requestContext = Communicator.get().requestFactory.createSubjectRequest();
		SubjectProxy subject = requestContext.create(SubjectProxy.class);
		subject.setName(nameSetter.getValue());
		subject.setDisplayName(displayNameSetter.getValue());
		requestContext.putSubject(subject).
		fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("editButton")
	void onEdit(ClickEvent e) {
		SubjectProxy subject = dataGrid.getSelected();
		SubjectRequest request = Communicator.get().requestFactory.createSubjectRequest();
		subject = request.edit(subject);
		subject.setName(nameSetter.getValue());
		subject.setDisplayName(displayNameSetter.getValue());
		request.putSubject(subject).fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("removeButton")
	void onRemove(ClickEvent e) {
		SubjectProxy subject = dataGrid.getSelected();
		SubjectRequest request = Communicator.get().requestFactory.createSubjectRequest();
		request.deleteSubject(subject.getId()).fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("cancelButton")
	void onCancel(ClickEvent e) {
		SubjectProxy subject = dataGrid.getSelected();
		dataGrid.getSelectionModel().setSelected(subject, false);
		setCreatingMode();
	}
	
	private void setCreatingMode() {
		saveButton.setVisible(true);
		editButton.setVisible(false);
		removeButton.setVisible(false);
		cancelButton.setVisible(false);
		nameSetter.setText("");
		displayNameSetter.setText("");
	}
	
	private void setEditingMode(SubjectProxy entity) {
		saveButton.setVisible(false);
		editButton.setVisible(true);
		removeButton.setVisible(true);
		cancelButton.setVisible(true);
		nameSetter.setText(entity.getName());
		displayNameSetter.setText(entity.getDisplayName());
	}
	
	private void updateList() {
		dataGrid.getProvider().update();
		setCreatingMode();
	}

	@Override
	public void onRowSelected(SubjectProxy entity) {
		setEditingMode(entity);
	}
	
	
	/**---------------------------------------------------------- 
	 * What will be done when data is loaded
	 * */
	private class PostLoadedAction implements Runnable {
		@Override
		public void run() {
			nameSetter.setText("");
			displayNameSetter.setText("");
		}
	}

}
