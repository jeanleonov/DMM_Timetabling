package com.timetabling.client.pages.cathedra;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.base.datagrid.DataSelectionListener;
import com.timetabling.client.communication.entities.CathedraProxy;
import com.timetabling.client.communication.requests.CathedraRequest;
import com.timetabling.client.pages.cathedra.table.CathedraGrid;

public class CathedraPage extends Composite implements DataSelectionListener<CathedraProxy> {

	private static CathedraPageUiBinder uiBinder = GWT
			.create(CathedraPageUiBinder.class);

	interface CathedraPageUiBinder extends UiBinder<Widget, CathedraPage> {
	}

	@UiField Button saveButton;
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
	void onClick(ClickEvent e) {
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
	
	private void updateList() {
		dataGrid.getProvider().update();
	}

	@Override
	public void onRowSelected(CathedraProxy entity) {
		nameSetter.setText(entity.getName());
		emailSetter.setText(entity.getEmail());
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
