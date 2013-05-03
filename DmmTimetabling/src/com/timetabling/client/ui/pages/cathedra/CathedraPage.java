package com.timetabling.client.ui.pages.cathedra;

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
import com.timetabling.client.communication.requests.CathedraRequest;
import com.timetabling.client.ui.pages.BasePage;
import com.timetabling.client.ui.pages.cathedra.table.CathedraGrid;

public class CathedraPage extends BasePage implements DataSelectionListener<CathedraProxy> {

	private static CathedraPageUiBinder uiBinder = GWT
			.create(CathedraPageUiBinder.class);

	interface CathedraPageUiBinder extends UiBinder<Widget, CathedraPage> {
	}

	@UiField Button saveButton;
	@UiField Button editButton;
	@UiField Button removeButton;
	@UiField Button cancelButton;
	@UiField Button	updateButton;
	@UiField TextBox nameSetter;
	@UiField TextBox emailSetter;
	@UiField FlowPanel persistedCathedras;
	private CathedraGrid dataGrid;

	public CathedraPage() {
		initWidget(uiBinder.createAndBindUi(this));
		dataGrid = new CathedraGrid(this);
		dataGrid.setPreSucces(new PostLoadedAction());
		persistedCathedras.add(dataGrid);
		dataGrid.getElement().getStyle().setHeight(100, Unit.PCT);
		updateList();
	}

	@UiHandler("saveButton")
	void onSave(ClickEvent e) {
		CathedraRequest requestContext = Communicator.get().requestFactory.createCathedraRequest();
		CathedraProxy cathedra = requestContext.create(CathedraProxy.class);
		cathedra.setName(nameSetter.getValue());
		cathedra.setEmail(emailSetter.getValue());
		requestContext.putCathedra(cathedra).
		fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("editButton")
	void onEdit(ClickEvent e) {
		CathedraProxy cathedra = dataGrid.getSelected();
		CathedraRequest request = Communicator.get().requestFactory.createCathedraRequest();
		cathedra = request.edit(cathedra);
		cathedra.setName(nameSetter.getValue());
		cathedra.setEmail(emailSetter.getValue());
		request.putCathedra(cathedra).fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("removeButton")
	void onRemove(ClickEvent e) {
		CathedraProxy cathedra = dataGrid.getSelected();
		CathedraRequest request = Communicator.get().requestFactory.createCathedraRequest();
		request.deleteCathedra(cathedra.getId()).fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				updateList();
			}
		});
	}

	@UiHandler("cancelButton")
	void onCancel(ClickEvent e) {
		CathedraProxy cathedra = dataGrid.getSelected();
		dataGrid.getSelectionModel().setSelected(cathedra, false);
		setCreatingMode();
	}
	
	@UiHandler("updateButton")
	void onUpdate(ClickEvent e) {
		dataGrid.getProvider().update();
	}
	
	private void setCreatingMode() {
		saveButton.setVisible(true);
		editButton.setVisible(false);
		removeButton.setVisible(false);
		cancelButton.setVisible(false);
		nameSetter.setText("");
		emailSetter.setText("");
	}
	
	private void setEditingMode(CathedraProxy entity) {
		saveButton.setVisible(false);
		editButton.setVisible(true);
		removeButton.setVisible(true);
		cancelButton.setVisible(true);
		nameSetter.setText(entity.getName());
		emailSetter.setText(entity.getEmail());
	}
	
	private void updateList() {
		dataGrid.getProvider().update();
		setCreatingMode();
	}

	@Override
	public void onRowSelected(CathedraProxy entity) {
		setEditingMode(entity);
	}
	
	
	/**---------------------------------------------------------- 
	 * What will be done when data is loaded
	 * */
	private class PostLoadedAction implements Runnable {
		@Override
		public void run() {
			nameSetter.setText("");
			emailSetter.setText("");
		}
	}

}
