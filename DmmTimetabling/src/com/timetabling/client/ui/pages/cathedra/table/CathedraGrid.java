package com.timetabling.client.ui.pages.cathedra.table;

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

public class CathedraGrid extends BaseDataGrid<CathedraProxy> {

	public CathedraGrid(DataSelectionListener<CathedraProxy> selectionListener) {
		super(selectionListener);
	}
	
	@Override
	protected void initiateColumns() {
		Column<CathedraProxy, String> cathedraName = new NameColumn();
		addColumn(cathedraName, "Имя кафедры", "300px");
		Column<CathedraProxy, String> cathedraEmail = new EmailColumn();
		addColumn(cathedraEmail, "email", "200px");
		getElement().getStyle().setWidth(510, Unit.PX);
	}

	@Override
	protected BaseAsyncDataProvider<CathedraProxy> createDataProvider() {
		return new CathedraAsyncDataProvider();
	}

	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Cathedra.name
	 * */
	public static class NameColumn extends Column<CathedraProxy, String> {
		public NameColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CathedraProxy cathedra) {
				return cathedra.getName();
		}
	}

	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Cathedra.email
	 * */
	public static class EmailColumn extends Column<CathedraProxy, String> {
		public EmailColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(CathedraProxy cathedra) {
			return cathedra.getEmail();
		}
	}
	
	
	/**---------------------------------------------------------- 
	 * Implementation of Asynchronous data provider for Cathedras 
	 * */
	public static class CathedraAsyncDataProvider extends BaseAsyncDataProvider<CathedraProxy> {
		@Override
		public ProvidesKey<CathedraProxy> getKeyProvider() {
			return new CathedraKeyProvider();
		}
		@Override
		public Request<List<CathedraProxy>> createListRequest() {
			return Communicator.get().requestFactory.createCathedraRequest().getAllCathedras();
		}
	}
	

	/**-------------------------------------------- 
	 * Implementation of Key provider for Cathedras 
	 * */
	public static class CathedraKeyProvider implements ProvidesKey<CathedraProxy> {
		@Override
		public Object getKey(CathedraProxy item) {
			return item.stableId();
		}
	}
}
