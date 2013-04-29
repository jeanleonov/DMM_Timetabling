package com.timetabling.client.ui.pages.specialty;

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
import com.timetabling.client.communication.entities.SpecialtyProxy;
import com.timetabling.client.communication.requests.SpecialtyRequest;
import com.timetabling.client.ui.pages.BasePage;
import com.timetabling.client.ui.pages.specialty.table.SpecialtyGrid;

public class SpecialtyPage extends BasePage implements DataSelectionListener<SpecialtyProxy> {

	private static SpecialtyPageUiBinder uiBinder = GWT
			.create(SpecialtyPageUiBinder.class);

	interface SpecialtyPageUiBinder extends UiBinder<Widget, SpecialtyPage> {
	}

	@UiField Button saveButton;
	@UiField Button editButton;
	@UiField Button removeButton;
	@UiField Button cancelButton;
	@UiField TextBox nameSetter;
	@UiField TextBox shortNameSetter;
	@UiField FlowPanel persistedSpecialties;
	private SpecialtyGrid dataGrid;

	public SpecialtyPage() {
		initWidget(uiBinder.createAndBindUi(this));
		dataGrid = new SpecialtyGrid(this);
		dataGrid.setPreSucces(new PostLoadedAction());
		persistedSpecialties.add(dataGrid);
		dataGrid.getElement().getStyle().setHeight(100, Unit.PCT);
		updateList();
	}

	@UiHandler("saveButton")
	void onSave(ClickEvent e) {
		SpecialtyRequest requestContext = Communicator.get().requestFactory.createSpecialtyRequest();
		SpecialtyProxy specialty = requestContext.create(SpecialtyProxy.class);
		specialty.setName(nameSetter.getValue());
		specialty.setShortName(shortNameSetter.getValue());
		requestContext.putSpecialty(specialty).
		fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("editButton")
	void onEdit(ClickEvent e) {
		SpecialtyProxy specialty = dataGrid.getSelected();
		SpecialtyRequest request = Communicator.get().requestFactory.createSpecialtyRequest();
		specialty = request.edit(specialty);
		specialty.setName(nameSetter.getValue());
		specialty.setShortName(shortNameSetter.getValue());
		request.putSpecialty(specialty).fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("removeButton")
	void onRemove(ClickEvent e) {
		SpecialtyProxy specialty = dataGrid.getSelected();
		SpecialtyRequest request = Communicator.get().requestFactory.createSpecialtyRequest();
		request.deleteSpecialty(specialty.getId()).fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("cancelButton")
	void onCancel(ClickEvent e) {
		SpecialtyProxy specialty = dataGrid.getSelected();
		dataGrid.getSelectionModel().setSelected(specialty, false);
		setCreatingMode();
	}
	
	private void setCreatingMode() {
		saveButton.setVisible(true);
		editButton.setVisible(false);
		removeButton.setVisible(false);
		cancelButton.setVisible(false);
		nameSetter.setText("");
		shortNameSetter.setText("");
	}
	
	private void setEditingMode(SpecialtyProxy entity) {
		saveButton.setVisible(false);
		editButton.setVisible(true);
		removeButton.setVisible(true);
		cancelButton.setVisible(true);
		nameSetter.setText(entity.getName());
		shortNameSetter.setText(entity.getShortName());
	}
	
	private void updateList() {
		dataGrid.getProvider().update();
		setCreatingMode();
	}

	@Override
	public void onRowSelected(SpecialtyProxy entity) {
		setEditingMode(entity);
	}
	
	
	/**---------------------------------------------------------- 
	 * What will be done when data is loaded
	 * */
	private class PostLoadedAction implements Runnable {
		@Override
		public void run() {
			nameSetter.setText("");
			shortNameSetter.setText("");
		}
	}

}
