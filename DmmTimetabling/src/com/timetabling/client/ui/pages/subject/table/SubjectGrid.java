package com.timetabling.client.ui.pages.subject.table;

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
import com.timetabling.client.communication.entities.SubjectProxy;

public class SubjectGrid extends BaseDataGrid<SubjectProxy> {

	public SubjectGrid(DataSelectionListener<SubjectProxy> selectionListener) {
		super(selectionListener);
	}
	
	@Override
	protected void initiateColumns() {
		Column<SubjectProxy, String> subjectName = new NameColumn();
		addColumn(subjectName, "Subject name", "300px");
		Column<SubjectProxy, String> displayName = new DisplayNameColumn();
		addColumn(displayName, "Display name", "200px");
		getElement().getStyle().setWidth(510, Unit.PX);
	}

	@Override
	protected BaseAsyncDataProvider<SubjectProxy> createDataProvider() {
		return new SubjectAsyncDataProvider();
	}

	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Subject.name
	 * */
	public static class NameColumn extends Column<SubjectProxy, String> {
		public NameColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(SubjectProxy specialty) {
			return specialty.getName();
		}
	}

	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Subject.displayName
	 * */
	public static class DisplayNameColumn extends Column<SubjectProxy, String> {
		public DisplayNameColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(SubjectProxy subject) {
			return subject.getDisplayName();
		}
	}
	
	
	/**---------------------------------------------------------- 
	 * Implementation of Asynchronous data provider for Subject 
	 * */
	public static class SubjectAsyncDataProvider extends BaseAsyncDataProvider<SubjectProxy> {
		@Override
		public ProvidesKey<SubjectProxy> getKeyProvider() {
			return new SubjectKeyProvider();
		}
		@Override
		public Request<List<SubjectProxy>> createListRequest() {
			return Communicator.get().requestFactory.createSubjectRequest().getAllSubjects();
		}
	}
	

	/**-------------------------------------------- 
	 * Implementation of Key provider for Subject 
	 * */
	public static class SubjectKeyProvider implements ProvidesKey<SubjectProxy> {
		@Override
		public Object getKey(SubjectProxy item) {
			return item.getId();
		}
	}
}
