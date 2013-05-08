package com.timetabling.client.ui.pages.specialty.table;

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
import com.timetabling.client.communication.entities.SpecialtyProxy;

public class SpecialtyGrid extends BaseDataGrid<SpecialtyProxy> {

	public SpecialtyGrid(DataSelectionListener<SpecialtyProxy> selectionListener) {
		super(selectionListener);
	}
	
	@Override
	protected void initiateColumns() {
		Column<SpecialtyProxy, String> specialtyName = new NameColumn();
		addColumn(specialtyName, "Имя специальности", "300px");
		Column<SpecialtyProxy, String> shartName = new ShortNameColumn();
		addColumn(shartName, "Краткое имя", "200px");
		getElement().getStyle().setWidth(510, Unit.PX);
	}

	@Override
	protected BaseAsyncDataProvider<SpecialtyProxy> createDataProvider() {
		return new SpecialtyAsyncDataProvider();
	}

	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Specialty.name
	 * */
	public static class NameColumn extends Column<SpecialtyProxy, String> {
		public NameColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(SpecialtyProxy specialty) {
			return specialty.getName();
		}
	}

	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Specialty.shortName
	 * */
	public static class ShortNameColumn extends Column<SpecialtyProxy, String> {
		public ShortNameColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(SpecialtyProxy specialty) {
			return specialty.getShortName();
		}
	}
	
	
	/**---------------------------------------------------------- 
	 * Implementation of Asynchronous data provider for Specialty 
	 * */
	public static class SpecialtyAsyncDataProvider extends BaseAsyncDataProvider<SpecialtyProxy> {
		@Override
		public ProvidesKey<SpecialtyProxy> getKeyProvider() {
			return new SpecialtyKeyProvider();
		}
		@Override
		public Request<List<SpecialtyProxy>> createListRequest() {
			return Communicator.get().requestFactory.createSpecialtyRequest().getAllSpecialties();
		}
	}
	

	/**-------------------------------------------- 
	 * Implementation of Key provider for Specialty 
	 * */
	public static class SpecialtyKeyProvider implements ProvidesKey<SpecialtyProxy> {
		@Override
		public Object getKey(SpecialtyProxy item) {
			return item.getId();
		}
	}
}
