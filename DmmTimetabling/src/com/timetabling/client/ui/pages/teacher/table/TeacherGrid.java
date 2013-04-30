package com.timetabling.client.ui.pages.teacher.table;

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
import com.timetabling.client.communication.entities.TeacherProxy;
import com.timetabling.shared.enums.TeacherRank;

public class TeacherGrid extends BaseDataGrid<TeacherProxy> {
	
	private CathedraProxy cathedra = null;

	public TeacherGrid(DataSelectionListener<TeacherProxy> selectionListener) {
		super(selectionListener);
	}
	
	@Override
	protected void initiateColumns() {
		Column<TeacherProxy, String> teacherName = new NameColumn();
		addColumn(teacherName, "Teacher name", "300px");
		Column<TeacherProxy, String> teacherRank = new RankColumn();
		addColumn(teacherRank, "Rank", "200px");
		getElement().getStyle().setWidth(510, Unit.PX);
	}

	@Override
	protected BaseAsyncDataProvider<TeacherProxy> createDataProvider() {
		return new TeacherAsyncDataProvider();
	}
	
	public CathedraProxy getCathedra() {
		return cathedra;
	}

	public void setCathedra(CathedraProxy cathedra) {
		this.cathedra = cathedra;
	}

	/**---------------------------------------------------------- 
	 * Implementation of Column for Teacher.name
	 * */
	public static class NameColumn extends Column<TeacherProxy, String> {
		public NameColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(TeacherProxy teacher) {
			return teacher.getName();
		}
	}

	
	/**---------------------------------------------------------- 
	 * Implementation of Column for Teacher.rank
	 * */
	public static class RankColumn extends Column<TeacherProxy, String> {
		public RankColumn() {
			super(new TextCell());
		}
		@Override
		public String getValue(TeacherProxy teacher) {
			if (teacher.getRankCode() == -1)
				return "";
			return TeacherRank.getByCode(teacher.getRankCode()).getDisplayName();
		}
	}
	
	
	/**---------------------------------------------------------- 
	 * Implementation of Asynchronous data provider for Teacher 
	 * */
	public class TeacherAsyncDataProvider extends BaseAsyncDataProvider<TeacherProxy> {
		public TeacherAsyncDataProvider() {}
		@Override
		public ProvidesKey<TeacherProxy> getKeyProvider() {
			return new TeacherKeyProvider();
		}
		@Override
		public Request<List<TeacherProxy>> createListRequest() {
			return Communicator.get().requestFactory.createTeacherRequest().getAllTeachersFrom(cathedra);
		}
		@Override
		public void update() {
			if (cathedra != null)
				super.update();
		}
	}
	

	/**-------------------------------------------- 
	 * Implementation of Key provider for Teacher 
	 * */
	public static class TeacherKeyProvider implements ProvidesKey<TeacherProxy> {
		@Override
		public Object getKey(TeacherProxy item) {
			return item.stableId();
		}
	}
}
